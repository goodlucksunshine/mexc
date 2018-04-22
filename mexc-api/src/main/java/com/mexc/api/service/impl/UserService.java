package com.mexc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.MD5Util;
import com.laile.esf.common.util.StringUtil;
import com.mexc.api.constant.ResultCode;
import com.mexc.api.service.IUserService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.member.*;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.PwdUtil;
import com.mexc.common.util.RandomUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberAuthDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.dto.member.LoginInfoDto;
import com.mexc.dao.dto.vcoin.BtcValueDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import com.mexc.dao.util.mail.MailContent;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.mexc.common.constant.RedisKeyConstant.REG_;

/**
 * Created by huangxinguang on 2018/2/1 下午4:50.
 * 用户
 */
@Service
public class UserService implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private MemberAssetDelegate memberAssetDelegate;

    @Autowired
    private MemberAuthDelegate memberAuthDelegate;

    @Resource
    private IMqProducerService mqProducerService;

    @Resource
    private FastJsonMessageConverter fastJsonMessageConverter;

    @Value("${mq.queue.asset}")
    private String createAsset;

    private Executor executor = Executors.newFixedThreadPool(15);

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        try {
            logger.info(LogUtil.msg("UserService:login", "用户[" + loginRequest.getMailName() + "]开始登陆"));

            /**验证用户名密码*/
            MexcMember mexcMember = memberDelegate.queryMermberByAccountAndPwd(loginRequest.getMailName(), PwdUtil.getMemberPwd(loginRequest.getMailName(), loginRequest.getPassword()));
            if (mexcMember == null) {
                logger.info(LogUtil.msg("UserService:login", "用户[" + loginRequest.getMailName() + "]不存在或密码错误"));
                response.setResultCode(ResultCode.MEXC_API_00002.getCode());
                response.setMessage(ResultCode.MEXC_API_00002.getMsg());
                return response;
            }

            /** 更新用户信息 **/
            LoginInfoDto loginInfoDto = new LoginInfoDto();
            loginInfoDto.setAccount(loginRequest.getMailName());
            loginInfoDto.setLoginIp(loginRequest.getIp());
            loginInfoDto.setLoginTime(new Date());
            memberDelegate.updateLoginInfo(loginInfoDto);
            response.setPassword(loginRequest.getPassword());
            response.setUserId(mexcMember.getId());
            response.setIsGoogleAuthed(mexcMember.getAuthLevel() == 2 ? 1 : 0);
            response.setEmail(loginRequest.getMailName());

            /**查看是否身份认证*/
            MexcMemberAuth memberAuth = memberAuthDelegate.queryIdenAuthByMemberId(mexcMember.getId());
            if (memberAuth != null && memberAuth.getStatus() == 1) {
                response.setIsIdentityAuthed(1);
            } else {
                response.setIsIdentityAuthed(0);
            }


            /** 查询币种资产 **/
            List<Map<String, Object>> otherProperties = new ArrayList<>();
            AssetQueryDto assetQueryDto = new AssetQueryDto();
            assetQueryDto.setAccount(mexcMember.getAccount());
            List<AssetDto> assetList = memberAssetDelegate.queryMermberAsset(assetQueryDto);
            if (!CollectionUtils.isEmpty(assetList)) {
                for (AssetDto assetDto : assetList) {
                    Map<String, Object> vcoinMap = new HashMap<>();
                    vcoinMap.put(assetDto.getVcoinNameEn(), assetDto.getTotalAmount());
                    otherProperties.add(vcoinMap);
                }
            }
            response.setOtherProperties(otherProperties);

            /**美元和BTC估值*/
            Map<String,String> valueMap = this.queryBtcAndUsdValue(assetQueryDto);
            if(valueMap != null) {
                response.setBtcProperties(valueMap.get("btcValue"));
                response.setUsdProperties(valueMap.get("usdValue"));
            }

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
            logger.info(LogUtil.msg("UserService:login", "用户[" + loginRequest.getMailName() + "]登录成功"));
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("登陆异常",e);
            return response;
        }
        return response;
    }

    /**
     * 发送邮箱验证码
     * @param request
     * @return
     */
    public BaseResponse sendAuthCode(AuthCodeRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            MailContent mailContent = new MailContent();
            mailContent.setUserTo(new String[]{request.getEmailName()});
            mailContent.setTitle("注册验证码");
            String code = RandomUtil.getRandom();
            RedisUtil.setex(REG_ + request.getEmailName(), code, 86400);
            mailContent.setBody(MailSender.MAIL_AUTH_CODE.replace("${code}", code));

            MailSender.sendSingleMail(mailContent);

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("获取验证码异常",e);
            return response;
        }
        return response;
    }

    /**
     * 注册
     * @param registerRequest
     * @return
     */
    public BaseResponse register(RegisterRequest registerRequest) {
        BaseResponse response = new BaseResponse();
        try {
            /** 验证码不正确 **/
            String code = RedisUtil.get(REG_ + registerRequest.getEmailName());
            if (StringUtil.isEmpty(code) || code.equals(registerRequest.getAuthcode())) {
                response.setResultCode(ResultCode.MEXC_API_00003.getCode());
                response.setMessage(ResultCode.MEXC_API_00003.getMsg());
                return response;
            }

            /** 查询账户是否已经存在 **/
            MexcMember mexcMember = memberDelegate.queryMermberByAccount(registerRequest.getEmailName());
            if (mexcMember != null) {
                logger.info(LogUtil.msg("UserService:register", "用户[" + mexcMember.getAccount() + "]已存在"));
                response.setResultCode(ResultCode.MEXC_API_00004.getCode());
                response.setMessage(ResultCode.MEXC_API_00004.getMsg());
                return response;
            }

            /** 插入账号信息 */
            MexcMember register = new MexcMember();
            String uuid = UUIDUtil.get32UUID();
            register.setId(uuid);
            register.setStatus(0);
            register.setAccount(registerRequest.getEmailName());
            register.setAccountPwd(PwdUtil.getMemberPwd(registerRequest.getEmailName(), registerRequest.getPassword()));
            register.setAuthLevel(1);
            register.setCreateTime(new Date());
            int result = memberDelegate.insertSelective(register);
            register.setId(uuid);
            if (result < 1) {
                logger.info(LogUtil.msg("UserService:register", "用户[" + registerRequest.getEmailName() + "]插入失败,请检查是否已存在"));
                response.setResultCode(ResultCode.MEXC_API_00004.getCode());
                response.setMessage(ResultCode.MEXC_API_00004.getMsg());
                return response;
            }

            /** 创建用户资产 **/
            createAsset(register);

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("注册异常",e);
            return response;
        }

        return response;
    }

    /**
     * 重置密码
     * @param resetPwdRequest
     * @return
     */
    public BaseResponse resetPwd(ResetPwdRequest resetPwdRequest) {
        BaseResponse response = new BaseResponse();
        try {
            MexcMember member = memberDelegate.queryMermberByAccount(resetPwdRequest.getEmailName());
            member.setAccountPwd(PwdUtil.getMemberPwd(resetPwdRequest.getEmailName(), resetPwdRequest.getnPassword()));
            int result = memberDelegate.updateByPrimaryKeySelective(member);
            if (result < 1) {
                logger.info(LogUtil.msg("UserService:resetPwd", "用户[" + resetPwdRequest.getEmailName() + "]更新密码失败"));
                response.setResultCode(ResultCode.MEXC_API_00005.getCode());
                response.setMessage(ResultCode.MEXC_API_00005.getMsg());
                return response;
            }
            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            logger.error("重置密码异常",e);
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            return response;
        }

        return response;
    }

    /**
     * 修改密码
     * @param changePwdRequest
     * @return
     */
    public BaseResponse changePwd(ChangePwdRequest changePwdRequest) {
        BaseResponse response = new BaseResponse();
        try {
            /** 验证用户 */
            MexcMember member = memberDelegate.selectByPrimaryKey(changePwdRequest.getUserId());
            if (member == null) {
                logger.info(LogUtil.msg("UserService:changePwd", "用户[" + changePwdRequest.getUserId() + "]不存在"));
                response.setResultCode(ResultCode.MEXC_API_00006.getCode());
                response.setMessage(ResultCode.MEXC_API_00006.getMsg());
                return response;
            }

            /** 验证旧密码 */
            MexcMember mexcMember = memberDelegate.queryMermberByAccountAndPwd(member.getAccount(), PwdUtil.getMemberPwd(member.getAccount(), changePwdRequest.getOldPassword()));
            if (mexcMember == null) {
                logger.info(LogUtil.msg("UserService:changePwd", "用户[" + member.getAccount() + "]旧密码不正确"));
                response.setResultCode(ResultCode.MEXC_API_00007.getCode());
                response.setMessage(ResultCode.MEXC_API_00007.getMsg());
                return response;
            }

            /**更新密码*/
            mexcMember.setAccountPwd(PwdUtil.getMemberPwd(member.getAccount(), changePwdRequest.getNewPassword()));
            int result = memberDelegate.updateByPrimaryKeySelective(mexcMember);
            if (result < 1) {
                logger.info(LogUtil.msg("UserService:changePwd", "用户[" + member.getAccount() + "]修改密码失败"));
                response.setResultCode(ResultCode.MEXC_API_00008.getCode());
                response.setMessage(ResultCode.MEXC_API_00008.getMsg());
                return response;
            }

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("修改密码异常",e);
            return response;
        }

        return response;
    }


    /**
     * 获取用户信息
     * @param userInfoRequest
     * @return
     */
    public UserInfoResponse getUserInfo(UserInfoRequest userInfoRequest) {
        UserInfoResponse response = new UserInfoResponse();
        try {
            /** 验证用户 */
            MexcMember member = memberDelegate.selectByPrimaryKey(userInfoRequest.getUserId());
            if (member == null) {
                logger.info(LogUtil.msg("UserService:getUserInfo", "用户[" + userInfoRequest.getUserId() + "]不存在"));
                response.setResultCode(ResultCode.MEXC_API_00006.getCode());
                response.setMessage(ResultCode.MEXC_API_00006.getMsg());
                return response;
            }
            response.setUserId(member.getId());
            response.setEmail(member.getAccount());

            /** 查询币种资产 **/
            List<Map<String, Object>> otherProps = new ArrayList<>();
            AssetQueryDto assetQueryDto = new AssetQueryDto();
            assetQueryDto.setAccount(member.getAccount());
            List<AssetDto> assetList = memberAssetDelegate.queryMermberAsset(assetQueryDto);
            if (!CollectionUtils.isEmpty(assetList)) {
                for (AssetDto assetDto : assetList) {
                    Map<String, Object> coinValueMap = new HashMap<>();
                    coinValueMap.put(assetDto.getVcoinNameEn(), assetDto.getTotalAmount());
                    otherProps.add(coinValueMap);
                }
            }
            response.setOtherCoinProps(otherProps);

            /**美元和BTC估值*/
            Map<String,String> valueMap = this.queryBtcAndUsdValue(assetQueryDto);
            if(valueMap != null) {
                response.setBtcProperties(valueMap.get("btcValue"));
                response.setUsdPropertis(valueMap.get("usdValue"));
            }

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("获取用户信息异常",e);
            return response;
        }

        return response;
    }

    /**
     * 查询用户btc估值和美元估值
     * @param assetQueryDto
     * @return
     */
    private Map<String,String> queryBtcAndUsdValue(AssetQueryDto assetQueryDto) {
        Map<String,String> result = new HashMap<>();
        List<AssetDto> assetList = memberAssetDelegate.queryMermberAsset(assetQueryDto);
        BigDecimal sumBtcValue = new BigDecimal("0");//btc估值总和
        BigDecimal sumUsdValue;//美元估值总和
        BigDecimal currentUsdPrice = new BigDecimal("0");
        if(!CollectionUtils.isEmpty(assetList)) {
            for(AssetDto assetDto : assetList) {
                String key = assetDto.getVcoinNameEn()+"-"+assetDto.getVcoinId();
                String btcValueJson = RedisUtil.get(key);
                BtcValueDto btcValue = JSON.parseObject(btcValueJson, BtcValueDto.class);
                if(assetDto.getTotalAmount() == null){
                    assetDto.setTotalAmount(BigDecimal.ZERO);
                }
                if(btcValue != null) {
                    BigDecimal currentVCoinBtcValue = assetDto.getTotalAmount().multiply(new BigDecimal(btcValue.getPriceBtc()));
                    sumBtcValue = sumBtcValue.add(currentVCoinBtcValue);
                    currentUsdPrice = new BigDecimal(btcValue.getPriceUsb());
                }
            }
        }
        sumUsdValue = sumBtcValue.multiply(currentUsdPrice);//美元 = btc*美元估值
        result.put("usdValue",sumUsdValue.toPlainString());
        result.put("btcValue",sumBtcValue.toPlainString());
        return result;
    }

    private void createAsset(MexcMember mexcMember) {
        logger.info("开始创建用户资产：{}", JSON.toJSONString(mexcMember));
        executor.execute(() -> {
            MqMsgVo<Map<String, String>> mqMsgVo = new MqMsgVo<>();
            Map<String, String> map = new HashMap<>();
            map.put("account", mexcMember.getAccount());
            map.put("pwd", com.laile.esf.common.util.RandomUtil.randomStr(24));
            map.put("memberId", mexcMember.getId());
            map.put("iv", com.laile.esf.common.util.RandomUtil.randomStr(8));
            mqMsgVo.setMsgId(com.laile.esf.common.util.RandomUtil.randomStr(8))
                    .setContent(map)
                    .setInsertTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            logger.info(LogUtil.msg("UserServiceImpl:creatAsset", "异步发送创建用户资产队列，data:" + JSON.toJSONString(mqMsgVo)));
            try {
                mqProducerService.convertAndSend(createAsset, fastJsonMessageConverter.sendMessage(mqMsgVo));
            } catch (Exception e) {
                logger.error(LogUtil.msg("UserServiceImpl:creatAsset", "异步发送创建用户资产队列，data:" + JSON.toJSONString(map)), e);
            }
        });
    }

}
