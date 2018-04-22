package com.mexc.task.mq.trade;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.base.ResultVo;
import com.mexc.common.constant.ResCode;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.match.engine.service.IExchangeMatchService;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.web.core.service.order.IOrderService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc BTC市场交易队列消费者
 */
public class BtcMarketTradeConsumer implements ChannelAwareMessageListener {
    private Logger logger = LoggerFactory.getLogger("trade");

    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;

    @Resource
    IMqProducerService mqProducerService;

    @Resource
    IOrderService orderService;
    @Resource
    IExchangeMatchService exchangeMatchService;


    @Value("${mq.queue.btc.market.trade}")
    private String queueName;

    private static Map<String, Integer> errTimes = new HashMap<>();

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      /*  String key = "BtcMarketTradeConsumer";
        try {
            RedisUtil.lock(key, 500);*/
        if (message != null) {
            synchronized (this) {
                MDC.put("exeNo", TrackUtil.generateChannelFlowNo("BMT"));
                MqMsgVo<MatchOrder> mqMsgVo = fastJsonMessageConverter.fromMessage(message, new MqMsgVo<>());

                String jsonStr = JSON.toJSONString(mqMsgVo.getContent());
                MatchOrder entrustOrder = JSON.parseObject(jsonStr, MatchOrder.class);
                logger.info(LogUtil.msg("BtcMarketTradeConsumer:process", "收到BTC交易请求:" + JSON.toJSONString(mqMsgVo)));
                try {
                    //如果已经尝试3次撮合未成功直接返回 跳过
                    Integer times = errTimes.get(entrustOrder.getTradeNo());
                    if (times != null && times > 20) {
                        return;
                    }

                    MexcEnTrust entrustOrderFromDB = orderService.queryEntrust(entrustOrder.getTradeNo());
                    logger.info("委托单:{}", JSON.toJSONString(entrustOrderFromDB));
                    if (entrustOrderFromDB.getStatus() != 1 && entrustOrderFromDB.getStatus() != 3) {
                        logger.info("委托单号:" + entrustOrderFromDB.getTradeNo() + ",状态变化无法进行撮合,跳过");
                        return;
                    }
                    /** 调用撮合返回撮合结果 */
                    ResultVo<List<MatchOrder>> resultVo = exchangeMatchService.match(entrustOrder);
                    if (!ResCode.SUCCESS.equals(resultVo.getCode()) || resultVo.getData() == null) {
                        return;
                    }
                    /** 撮合并更新 */
                    orderService.doMatchAndUpdate(entrustOrder,resultVo);

                    logger.info(LogUtil.msg("BtcMarketTradeConsumer:process", "撮合处理成功：" + entrustOrder.getTradeNo()));
                } catch (Exception e) {
                    Integer times = errTimes.get(entrustOrder.getTradeNo());
                    if (times == null) {
                        times = 1;
                    } else {
                        times += 1;
                    }
                    errTimes.put(entrustOrder.getTradeNo(), times);
                    logger.error("撮合订单失败,请检查");
                    mqProducerService.convertAndSend(queueName, fastJsonMessageConverter.sendMessage(mqMsgVo));
                    logger.error(LogUtil.msg("BtcMarketTradeConsumer:process", "BTC交易异常,放入队列重新交易:" + JSON.toJSONString(mqMsgVo)), e);
                }
            }
        }
        if (errTimes != null && errTimes.size() > 0) {
            logger.info("errTime:{}", JSON.toJSONString(errTimes));
        }
       /* } catch (Exception e) {
            logger.error("ge redis lock exception.");
        }finally {
            RedisUtil.unLock(key);
        }*/

    }
}
