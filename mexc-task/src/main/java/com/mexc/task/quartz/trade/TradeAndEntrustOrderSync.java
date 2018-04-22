package com.mexc.task.quartz.trade;

import com.alibaba.fastjson.JSON;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.order.EntrustOrderQueryDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.vo.vcoin.entrust.EntrustOrder;
import com.mexc.dao.vo.vcoin.trade.TradeOrderVo;
import com.mexc.match.engine.util.QueueKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.parity.methods.response.VMTrace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxinguang on 2018/1/20 下午4:33.
 * 委托和交易订单同步
 */
public class TradeAndEntrustOrderSync {

    private static final Logger logger= LoggerFactory.getLogger(MarketAndVCoinSync.class);

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private MexcEnTrustDelegate mexcEnTrustDelegate;

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    public void sync() {
        List<MexcMarket> marketList = marketDelegate.queryMarketList();
        logger.info("同步 委托订单 list:{}", JSON.toJSONString(marketList));
        //同步买委托
        try {
            EntrustOrderQueryDto buyQueryDto;
            if (!CollectionUtils.isEmpty(marketList)) {
                for (MexcMarket market : marketList) {
                    List<MexcMarketVCoin> marketVCoinList = marketDelegate.queryMarketVcoinList(market.getId());
                    if (!CollectionUtils.isEmpty(marketVCoinList)) {
                        for (MexcMarketVCoin marketVCoin : marketVCoinList) {
                            buyQueryDto = new EntrustOrderQueryDto();
                            buyQueryDto.setTradeType("1");
                            buyQueryDto.setStatus("1,3");
                            buyQueryDto.setMarketId(market.getId());
                            buyQueryDto.setVcoinId(marketVCoin.getVcoinId());

                            String key = QueueKeyUtil.getKey(market.getId(), marketVCoin.getVcoinId(), buyQueryDto.getTradeType());

                            String buySum = mexcEnTrustDelegate.queryEntrustTradeOrderSum(buyQueryDto);//市场下某一币种的委托总和
                            List<EntrustOrder> list = mexcEnTrustDelegate.queryEntrustTradeOrderLimit(buyQueryDto, 15);//查市场下某一币种的委托信息
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            logger.info("填充redis key:" + CommonConstant.ENTRUST_BUY_PREFIX + key + ",value:" + JSON.toJSONString(list));
                            RedisUtil.set(CommonConstant.ENTRUST_BUY_PREFIX + key, JSON.toJSONString(list));
                            logger.info("填充redis key:" + CommonConstant.ENTRUST_BUY_SUM_PREFIX + key + ",value:" + buySum);
                            RedisUtil.set(CommonConstant.ENTRUST_BUY_SUM_PREFIX + key, buySum == null ? "0" : buySum);
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("同步委托单异常",e);
        }

        //同步卖委托缓存 和交易历史
        try {
            EntrustOrderQueryDto sellQueryDto;
            EntrustOrderQueryDto tradeQueryDto;
            if (!CollectionUtils.isEmpty(marketList)) {
                logger.info("同步卖委托缓存 和交易历史:{}", JSON.toJSONString(marketList));
                for (MexcMarket market : marketList) {
                    List<MexcMarketVCoin> marketVCoinList = marketDelegate.queryMarketVcoinList(market.getId());
                    if (!CollectionUtils.isEmpty(marketVCoinList)) {
                        for (MexcMarketVCoin marketVCoin : marketVCoinList) {

                            //委托卖
                            sellQueryDto = new EntrustOrderQueryDto();
                            sellQueryDto.setTradeType("2");
                            sellQueryDto.setStatus("1,3");
                            sellQueryDto.setMarketId(market.getId());
                            sellQueryDto.setVcoinId(marketVCoin.getVcoinId());

                            String key = QueueKeyUtil.getKey(market.getId(), marketVCoin.getVcoinId(), sellQueryDto.getTradeType());

                            String sellSum = mexcEnTrustDelegate.queryEntrustTradeOrderSum(sellQueryDto);//查市场下某一币种的委托信息
                            List<EntrustOrder> list = mexcEnTrustDelegate.queryEntrustTradeOrderLimit(sellQueryDto, 15);//市场下某一币种的委托总和
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            logger.info("同步卖委托缓存 和交易历史 redis:" + CommonConstant.ENTRUST_SELL_PREFIX + key + ",value:" + JSON.toJSONString(list));
                            RedisUtil.set(CommonConstant.ENTRUST_SELL_PREFIX + key, JSON.toJSONString(list));
                            RedisUtil.set(CommonConstant.ENTRUST_SELL_SUM_PREFIX + key, sellSum == null ? "0" : sellSum);


                            //交易历史
                            tradeQueryDto = new EntrustOrderQueryDto();
                            tradeQueryDto.setStatus("1");
                            tradeQueryDto.setVcoinId(marketVCoin.getVcoinId());
                            tradeQueryDto.setMarketId(market.getId());

                            String tradeHistoryKey = QueueKeyUtil.getKey(market.getId(), marketVCoin.getVcoinId());

                            List<TradeOrderVo> tradeList = mexcTradeDelegate.queryTradeOrderLimit(tradeQueryDto, 25);
                            RedisUtil.set(CommonConstant.TRADE_PREFIX + tradeHistoryKey, CollectionUtils.isEmpty(tradeList) ?
                                    JSON.toJSONString(new ArrayList<TradeOrderVo>()) : JSON.toJSONString(tradeList));
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("同步卖委托单和交易历史异常",e);
        }
    }
}
