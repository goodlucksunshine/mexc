package com.mexc.task.quartz;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.RandomUtil;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.vcoin.TokenEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/3/11
 * Time: 下午2:19
 */
public class EntrustOrderReSendCheck {
    private static Logger logger = LoggerFactory.getLogger(EntrustOrderReSendCheck.class);
    private static boolean lock = false;
    @Autowired
    private MexcEnTrustDelegate mexcEnTrustDelegate;
    @Resource
    MarketDelegate marketDelegate;
    @Resource
    MemberDelegate memberDelegate;
    @Resource
    VCoinDelegate vCoinDelegate;

    @Value("${mq.queue.btc.market.trade}")
    private String btcQueue;

    @Value("${mq.queue.eth.market.trade}")
    private String ethQueue;
    @Resource
    IMqProducerService mqProducerService;
    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;


    public void entrustCheck() {
        if (lock) {
            logger.info("上一次补单还未完成,直接返回");
            return;
        }
        lock = true;
        List<MexcEnTrust> enTrusts = mexcEnTrustDelegate.queryEntrustByMqStatus(50);
        if (CollectionUtils.isNotEmpty(enTrusts)) {
            for (MexcEnTrust mexcEnTrust : enTrusts) {
                MexcMarket market = marketDelegate.selectByPrimaryKey(mexcEnTrust.getMarketId());
                MexcMember member = memberDelegate.selectByPrimaryKey(mexcEnTrust.getMemberId());
                MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(mexcEnTrust.getTradeVcoinId());
                /** 组装委托单消息并发送 **/
                MatchOrder entrustOrder = new MatchOrder();
                entrustOrder.setTradeNo(mexcEnTrust.getTradeNo());
                entrustOrder.setEntrustNumber(mexcEnTrust.getTradeNumber());
                entrustOrder.setTradeType(mexcEnTrust.getTradeType());
                entrustOrder.setMarketId(market.getId());
                entrustOrder.setPrice(mexcEnTrust.getTradePrice());
                entrustOrder.setTradedNumber(mexcEnTrust.getTradeNumber().subtract(mexcEnTrust.getTradeRemainNumber()));
                entrustOrder.setTradableNumber(mexcEnTrust.getTradeRemainNumber());
                entrustOrder.setVcoinId(vCoin.getId());
                entrustOrder.setMemberId(member.getId());
                entrustOrder.setEntrustTime(mexcEnTrust.getCreateTime());
                MqMsgVo<MatchOrder> mqMsgVo = new MqMsgVo<>();

                mqMsgVo.setMsgId(RandomUtil.randomStr(8))
                        .setContent(entrustOrder)
                        .setInsertTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                int result = mexcEnTrustDelegate.updateMqStatus(entrustOrder.getTradeNo());
                if (result < 0) {
                    logger.warn("更改委托发送MQ异常:{}", entrustOrder.getTradeNo());
                }
                logger.info(LogUtil.msg("OrderServiceImpl:entrustTrade", "异步发送委托交易队列，data:" + JSON.toJSONString(mqMsgVo)));
                try {
                    if (market.getMarketName().equalsIgnoreCase(TokenEnum.BIT_COIN.getCode())) {
                        mqProducerService.convertAndSend(btcQueue, fastJsonMessageConverter.sendMessage(mqMsgVo));
                    } else if (market.getMarketName().equalsIgnoreCase(TokenEnum.ETH_COIN.getCode())) {
                        mqProducerService.convertAndSend(ethQueue, fastJsonMessageConverter.sendMessage(mqMsgVo));
                    }

                } catch (Exception e) {
                    logger.error(LogUtil.msg("OrderServiceImpl:entrustTrade", "异步发送委托交易队列，data:" + JSON.toJSONString(mqMsgVo)), e);
                }
            }
        }
        lock = false;
    }
}
