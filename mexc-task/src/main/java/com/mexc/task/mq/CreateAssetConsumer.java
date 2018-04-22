package com.mexc.task.mq;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcAddressLib;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.mq.core.IMqCustomerListener;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.task.AddressLibContext;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.web.core.service.asset.IAssetService;
import com.mexc.web.core.service.vcoin.IVcoinService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/15
 * Time: 下午3:33
 */
public class CreateAssetConsumer implements IMqCustomerListener {
    private static Logger logger = LoggerFactory.getLogger("createAsset");

    @Value("${eth_path}")
    private String eth_path;

    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;
    @Resource
    IVcoinService vcoinService;
    @Resource
    IAssetService assetService;

    @Override
    public void process(Message message) {
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("CAS"));
        if (!AddressLibContext.getInitStatus()) {
            try {
                Thread.sleep(10000l);
            } catch (InterruptedException e) {
                logger.info("线程沉睡异常");
            }
        }
        logger.info("====>开始处理用户注册队列==>" + JSON.toJSONString(message));
        if (message != null) {
            Date now = new Date();
            MqMsgVo<Map<String, String>> mqMsgVo = fastJsonMessageConverter.fromMessage(message, new MqMsgVo<>());
            Map<String, String> map = mqMsgVo.getContent();
            String memberId = map.get("memberId");
            String account = map.get("account");
            logger.info(LogUtil.msg("CreateAssetConsumer:process", "收到创建用户资产消息:" + JSON.toJSONString(mqMsgVo)));
            List<MexcVCoin> vCoinList = vcoinService.queryAllCanUseVcoin();
            Set<String> vcoinCategory = new HashSet<>();
            Map<String, MexcAddressLib> categoryMap = new HashMap<>();
            for (MexcVCoin vCoin : vCoinList) {
                vcoinCategory.add(vCoin.getVcoinToken());
            }
            Set<String> sourceSet = RedisUtil.sinter("BTC_ADDRESS_LIB_NOT_USED");
            if (sourceSet != null && sourceSet.size() > 0) {
                logger.info("谁特么把我删掉了");
            }
            logger.info("资产创建分组：{}", JSON.toJSONString(vcoinCategory));
            for (String category : vcoinCategory) {
                logger.info("category:{}", category);
                String addressInfo = AddressLibContext.sRandMember(category);
                logger.info("address:{}", addressInfo);
                if (addressInfo == null || StringUtils.isEmpty(addressInfo.toString())) {
                    logger.error("用户资产库资产地址不足");
                    MailSender.sendWarningMail("用户资产库资产地址不足 type:" + category);
                    continue;
                }
                AddressLibContext.srem(category, addressInfo);
                MexcAddressLib address = JSON.parseObject(addressInfo, MexcAddressLib.class);
                if (address.getType().equalsIgnoreCase("btc")) {
                    //生成钱包地址标签
                    String btcAddress = ThressDescUtil.decodeAssetAddress(address.getAddress());
                    BitServerUtil.setAccount(btcAddress, btcAddress);
                }
                categoryMap.put(category, address);
            }
            List<MexcMemberAsset> assets = new ArrayList<>();
            for (MexcVCoin vCoin : vCoinList) {
                MexcAddressLib address = categoryMap.get(vCoin.getVcoinToken());
                if (address == null) {
                    logger.info("未获取到此类型的资产地址===>" + JSON.toJSONString(vCoin));
                    continue;
                }
                logger.info("生成虚拟币资产===>" + JSON.toJSONString(vCoin));
                //生成对应的虚拟币钱包地址
                MexcMemberAsset asset = new MexcMemberAsset();
                asset.setId(UUIDUtil.get32UUID());
                asset.setBalanceAmount(BigDecimal.ZERO);
                asset.setAccount(account);
                asset.setBtcAmount(BigDecimal.ZERO);
                asset.setFrozenAmount(BigDecimal.ZERO);
                asset.setMemberId(memberId);
                asset.setStatus(1);
                asset.setIv(address.getIv());
                asset.setCreateTime(now);
                asset.setCreateBy("system");
                asset.setUpdateTime(now);
                asset.setUpdateBy("system");
                asset.setVcoinId(vCoin.getId());
                asset.setTotalAmount(BigDecimal.ZERO);
                asset.setWalletAddress(address.getAddress());
                asset.setAccountPwd(address.getPwd());
                asset.setToken(vCoin.getVcoinToken());
                asset.setFilePath(address.getFilepath());
                assets.add(asset);
            }
            assetService.addAsset(assets);
        }
    }
}
