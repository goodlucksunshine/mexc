package com.mexc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.RandomUtil;
import com.laile.esf.common.util.StringUtil;
import com.mexc.api.constant.ResultCode;
import com.mexc.api.service.IAssetService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.asset.*;
import com.mexc.common.BusCode;
import com.mexc.common.util.BigDecimalFormat;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.member.AddressMarkDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.member.MexcLevelDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.vcoin.VCoinFeeDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetCashDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetRechargeDelegate;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcLevel;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huangxinguang on 2018/2/6 上午10:21.
 * 资产
 */
@Service
public class AssetService implements IAssetService {

    private Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    private MexcAssetCashDelegate mexcAssetCashDelegate;

    @Autowired
    private MexcAssetRechargeDelegate mexcAssetRechargeDelegate;

    @Autowired
    private MemberAssetDelegate memberAssetDelegate;

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private VCoinDelegate vCoinDelegate;

    @Autowired
    private VCoinFeeDelegate vCoinFeeDelegate;

    @Autowired
    private MexcAssetCashDelegate assetCashDelegate;

    @Autowired
    private AddressMarkDelegate addressMarkDelegate;

    @Resource
    private IMqProducerService mqProducerService;

    @Resource
    private FastJsonMessageConverter fastJsonMessageConverter;

    @Value("${mq.queue.cash}")
    private String cashQue;

    @Autowired
    private MexcLevelDelegate levelDelegate;

    /**
     * 提现记录
     * @param cashRequest
     * @return
     */
    public CashResponse cashRecord(CashOrRechargeRequest cashRequest) {
        CashResponse response = new CashResponse();
        try {
            /**查询提现记录*/
            List<CashRecord> cashRecordList = new ArrayList<>();
            List<MexcAssetCash> cashList = mexcAssetCashDelegate.queryAssetCash(cashRequest.getUserId());
            if (!CollectionUtils.isEmpty(cashList)) {
                for (MexcAssetCash cash : cashList) {
                    CashRecord record = new CashRecord();
                    record.setRecordId(cash.getId());
                    record.setTxDate(cash.getTxApplyTime());
                    record.setTxPrice(cash.getTxAmount());
                    record.setTxAddress(cash.getTxCashAddress());
                    cashRecordList.add(record);
                }
            }
            response.setResultList(cashRecordList);
            response.setResultCode(0);
            response.setMessage("提现记录查询成功");
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("提现查询异常",e);
            return response;
        }

        return response;
    }

    /**
     * 查询充值记录
     * @param rechargeRequest
     * @return
     */
    public RechargeResponse rechargeRecord(CashOrRechargeRequest rechargeRequest) {
        RechargeResponse response = new RechargeResponse();
        try {

            /**查询充值记录*/
            List<RechargeRecord> rechargeRecordList = new ArrayList<>();
            List<MexcAssetRecharge> rechargeList = mexcAssetRechargeDelegate.queryAssetRecharge(rechargeRequest.getUserId());
            if (!CollectionUtils.isEmpty(rechargeList)) {
                for (MexcAssetRecharge recharge : rechargeList) {
                    RechargeRecord record = new RechargeRecord();
                    record.setTxHash(recharge.getTxHash());
                    record.setRechargeAddress(recharge.getRechargeAddress());
                    record.setRechargeTime(recharge.getReceiptTime());
                    record.setRechargeValue(recharge.getRechargeValue());
                    record.setTxToken(recharge.getTxToken());
                    rechargeRecordList.add(record);
                }
            }
            response.setResultList(rechargeRecordList);
            response.setResultCode(0);
            response.setMessage("充值记录查询成功");
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("充值查询异常",e);
            return response;
        }
        return response;
    }

    /**
     * 获取充值地址
     * @param rechargeAddressRequest
     * @return
     */
    public RechargeAddressResponse getRechargeAddress(RechargeAddressRequest rechargeAddressRequest) {
        RechargeAddressResponse response = new RechargeAddressResponse();
        try {
            MexcMemberAsset memberAsset = memberAssetDelegate.queryMemberAsset(rechargeAddressRequest.getUserId(), rechargeAddressRequest.getCurrencyId());
            if (memberAsset == null) {
                logger.info(LogUtil.msg("AssetService:getRechargeAddress", "用户没有该币种的地址"));
                response.setResultCode(2);
                response.setMessage("用户没有该币种的地址");
                return response;
            }

            response.setResultCode(0);
            response.setMessage("获取充值地址成功");
            response.setChargeAddress(ThressDescUtil.decodeAssetAddress(memberAsset.getWalletAddress()));
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("获取充值地址异常",e);
            return response;
        }
        return response;
    }

    /**
     * 提现
     * @param cashRequest
     * @return
     */
    public BaseResponse cash(CashRequest cashRequest) {
        BaseResponse response = new BaseResponse();
        try {
            /** 校验地址 */
            if (StringUtils.isEmpty(cashRequest.getTxAddress())) {
                response.setResultCode(1);
                response.setMessage("提现地址不能为空");
                return response;
            }

            /** 校验资产 */
            MexcMemberAsset asset = memberAssetDelegate.queryMemberAsset(cashRequest.getUserId(), cashRequest.getTxCurrencyId());
            BaseResponse checkResponse = assetCashCheck(asset.getId(), cashRequest.getTxCount());
            if (0 != checkResponse.getResultCode().intValue()) {
                return checkResponse;
            }

            Date now = new Date();
            MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(asset.getVcoinId());
            MexcVCoinFee vCoinFee = vCoinFeeDelegate.queryVcoinFeeByVcoinId(vCoin.getId());
            MexcMember mexcMember = memberDelegate.selectByPrimaryKey(cashRequest.getUserId());
            MexcAssetCash mexcAssetCash = new MexcAssetCash();
            String cashId = UUIDUtil.get32UUID();
            mexcAssetCash.setId(cashId);
            mexcAssetCash.setMemberId(mexcMember.getId());
            mexcAssetCash.setCashStatus(0);
            mexcAssetCash.setTxAmount(cashRequest.getTxCount());
            mexcAssetCash.setTxApplyTime(now);
            mexcAssetCash.setAssetAddress(asset.getWalletAddress());
            mexcAssetCash.setTxCashAddress(ThressDescUtil.encodeAssetAddress(cashRequest.getTxAddress()));
            mexcAssetCash.setTxApplyToken(vCoin.getVcoinToken());
            mexcAssetCash.setTxApplyStatus(1);
            mexcAssetCash.setTxApplyValue(new BigDecimal(cashRequest.getTxCount()));
            mexcAssetCash.setAssetId(asset.getId());
            mexcAssetCash.setTxApplyToken(vCoin.getVcoinName());
            mexcAssetCash.setCashFee(vCoinFee.getCashRate());
            BigDecimal cashValue = new BigDecimal(cashRequest.getTxCount());
            mexcAssetCash.setActualAmount(cashValue.subtract(vCoinFee.getCashRate()));
            String token = UUIDUtil.get32UUID();
            mexcAssetCash.setTxTransCode(token);
            mexcAssetCash.setCashStatus(0);
            List<MexcAssetTrans> transList = new ArrayList<>();
            MexcAssetTrans cashTrans = new MexcAssetTrans();
            cashTrans.setId(UUIDUtil.get32UUID());
            cashTrans.setAssetId(asset.getId());

            //-1:提现 1:冲值 2:委托 3:手续费
            cashTrans.setTradeType("-1");
            cashTrans.setTransAmount(cashValue);
            cashTrans.setTransTime(now);
            cashTrans.setTransNo(mexcAssetCash.getId());
            transList.add(cashTrans);
            MexcAssetTrans feeTrans = new MexcAssetTrans();
            feeTrans.setId(UUIDUtil.get32UUID());
            feeTrans.setAssetId(asset.getId());

            //-1:提现 1:冲值 2:委托 3:手续费
            feeTrans.setTradeType("3");
            feeTrans.setTransAmount(vCoinFee.getCashRate());
            feeTrans.setTransTime(now);
            feeTrans.setTransNo(mexcAssetCash.getId());
            transList.add(feeTrans);

            boolean result = assetCashDelegate.localUserAsset(mexcAssetCash, transList);
            if (!result) {
                response.setResultCode(2);
                response.setMessage("提现失败,请检查您是否有足够的资金。");
                return response;
            }

            try {
                addressMarkDelegate.saveHisAddress(mexcMember.getId(), cashRequest.getTxAddressTab(), asset.getId(), ThressDescUtil.encodeAssetAddress(cashRequest.getTxAddress()));
            } catch (Exception e) {
                logger.error("保存历史提现资产地址异常", e);
                response.setResultCode(3);
                response.setMessage("保存历史提现资产地址异常");
                return response;
            }

            MqMsgVo<Map<String, String>> mqMsgVo = new MqMsgVo<>();
            Map<String, String> map = new HashMap<>();
            map.put("cashId", cashId);
            mqMsgVo.setMsgId(RandomUtil.randomStr(8))
                    .setContent(map)
                    .setInsertTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            logger.info(LogUtil.msg("AssetService:cash", "异步发送创建用户资产队列，data:" + JSON.toJSONString(mqMsgVo)));
            mqProducerService.convertAndSend(cashQue, fastJsonMessageConverter.sendMessage(mqMsgVo));
            logger.info(LogUtil.msg("AssetService:cash", "异步发送创建用户资产队列，data:" + JSON.toJSONString(mqMsgVo)));

            response.setResultCode(0);
            response.setMessage("提现成功");
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("提现异常",e);
            return response;
        }
        return response;
    }


    private BaseResponse assetCashCheck(String assetId, String amount) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(assetId)) {
            response.setResultCode(5);
            response.setMessage("资产编号不能为空");
            return response;
        }
        if (StringUtils.isEmpty(amount)) {
            response.setResultCode(6);
            response.setMessage("数量不能为空");
            return response;
        }
        MexcMemberAsset asset = memberAssetDelegate.selectByPrimaryKey(assetId);
        MexcVCoinFee vCoinFee = vCoinFeeDelegate.queryVcoinFeeByVcoinId(asset.getVcoinId());
        MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(asset.getVcoinId());
        MexcMember mexcMember = memberDelegate.selectByPrimaryKey(asset.getMemberId());
        BigDecimal cashAmount = new BigDecimal(amount);
        if (cashAmount.compareTo(vCoinFee.getCashLimitMin()) < 0) {
            response.setResultCode(7);
            response.setMessage("不能小于最小提现数量 " + BigDecimalFormat.roundScale(vCoinFee.getCashLimitMin(), 8).stripTrailingZeros().toPlainString());
            return response;
        }

        if (cashAmount.compareTo(vCoinFee.getCashLimitMax()) > 1) {
            response.setResultCode(8);
            response.setMessage("不能小于最小提现数量 " + BigDecimalFormat.roundScale(vCoinFee.getCashLimitMin(), 8).stripTrailingZeros().toPlainString());
            return response;
        }

        if (cashAmount.compareTo(vCoinFee.getCashLimitMax()) > 1) {
            response.setResultCode(9);
            response.setMessage("不能大于最大提现数量 " + BigDecimalFormat.roundScale(vCoinFee.getCashLimitMin(), 8).stripTrailingZeros().toPlainString());
            return response;
        }
        BigDecimal assetBalance = asset.getBalanceAmount();
        //校验提现金额余额是否足够
        Map<String, String> checkResult = levelCashLimit(mexcMember, vCoin.getVcoinName(), cashAmount);
        if (checkResult != null) {
            String maxSurplus = checkResult.get("surplus");
            response.setResultCode(10);
            response.setMessage("用户等级限制,24小时提现不能大与" + maxSurplus + "比特币");
            return response;
        }
        if (assetBalance.compareTo(cashAmount) < 0) {
            response.setResultCode(11);
            response.setMessage("资产余额不足");
            return response;
        }

        response.setResultCode(0);

        return response;
    }

    private Map<String, String> levelCashLimit(MexcMember mexcMember, String token, BigDecimal cashValue) {
        String btcEstimate = RedisUtil.get(token);
        //拿不到估值  直接放过
        if (StringUtils.isEmpty(btcEstimate)) {
            return null;
        }
        MexcLevel level = levelDelegate.queryByLevel(mexcMember.getAuthLevel());
        //BigDecimal cashHis = assetCashDelegate.checkLevelLimit(mexcMember.getId());
        /*BigDecimal total = cashHis.add(cashValue);
        if (total.compareTo(level.getLimitAmount()) > 0) {
            BigDecimal maxSurplus = level.getLimitAmount().subtract(cashHis);
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("result", "false");
            returnMap.put("surplus", maxSurplus.stripTrailingZeros().toPlainString());
            return returnMap;
        } else {*/
            return null;
        //}
    }


}

