package com.mexc.task.mq;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetCashDelegate;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.vcoin.TokenEnum;
import com.mexc.web.core.service.asset.IAssetService;
import com.neemre.btcdcli4j.core.domain.Transaction;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/31
 * Time: 下午7:23
 */
public class AssetCashConsumer implements ChannelAwareMessageListener {
    private Logger logger = LoggerFactory.getLogger("assetCashConsumer");
    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;
    @Resource
    MexcAssetCashDelegate assetCashDelegate;
    @Resource
    MemberAssetDelegate memberAssetDelegate;
    @Resource
    VCoinDelegate vCoinDelegate;
    @Resource
    IAssetService assetService;

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("ACS"));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        logger.info("队列处理用户提现:{}", JSON.toJSONString(message));
        MqMsgVo<Map<String, String>> mqMsgVo = fastJsonMessageConverter.fromMessage(message, new MqMsgVo<>());
        logger.info("msg:{}", JSON.toJSONString(mqMsgVo));
        Map<String, String> map = mqMsgVo.getContent();
        String cashId = map.get("cashId");
        MexcAssetCash cash = assetCashDelegate.selectByPrimaryKey(cashId);
        MexcMemberAsset asset = memberAssetDelegate.selectByPrimaryKey(cash.getAssetId());
        MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(asset.getVcoinId());
        BigDecimal cashAmount = cash.getActualAmount();
        logger.info("钱包客户端开始发送命令");
        String transHash = "";
        if (vCoin.getVcoinToken().equalsIgnoreCase(TokenEnum.ETH_COIN.getCode())) {
            try {
                transHash = assetService.ethCash(vCoin, asset, ThressDescUtil.decodeAssetAddress(cash.getTxCashAddress()), cashAmount);
            } catch (Exception e) {
                logger.info("提现失败重新", e);
                boolean result = assetService.cashAutoFailed(cash);
                if (!result) {
                    MailSender.sendWarningMail("修改用户提现失败状态失败:" + JSON.toJSONString(cash));
                }
            }
            logger.info("eth钱包客户端开始发送命令返回:{}", JSON.toJSONString(transHash));
            if (StringUtils.isEmpty(transHash)) {
                logger.info("队列处理用户提现成功:{}", JSON.toJSONString(cash));
                boolean result = assetService.cashAutoFailed(cash);
                if (!result) {
                    MailSender.sendWarningMail("修改用户提现失败状态失败:" + JSON.toJSONString(cash));
                }
            }
        } else {
            Transaction transaction = assetService.btcCash(vCoin, asset, ThressDescUtil.decodeAssetAddress(cash.getTxCashAddress()), cashAmount);
            logger.info("比特钱包客户端开始发送命令返回:{}", JSON.toJSONString(transaction));
            if (transaction == null) {
                logger.info("队列处理用户提现成功:{}", JSON.toJSONString(cash));
                boolean result = assetService.cashAutoFailed(cash);
                if (!result) {
                    MailSender.sendWarningMail("修改用户提现失败状态失败:" + JSON.toJSONString(cash));
                }
            } else {
                transHash = transaction.getTxId();
            }
        }
        if (StringUtils.isNotEmpty(transHash)) {
            logger.info("更新提现 id:{} 交易hash:{}", cashId, transHash);
            int result = assetCashDelegate.updateCashHash(cashId, transHash);
            if (result > 0) {
                logger.info("更新提现 id:{} 交易hash:{} 成功", cashId, transHash);
            } else {
                logger.info("更新提现 id:{} 交易hash:{} 失败", cashId, transHash);
            }
        }
        logger.info("队列处理用户提现完成:{}", cashId);
    }
}
