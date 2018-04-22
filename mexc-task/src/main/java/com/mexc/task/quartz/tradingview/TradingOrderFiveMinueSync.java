package com.mexc.task.quartz.tradingview;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.SpringUtils;
import com.mexc.common.constant.TradingViewConstant;
import com.mexc.common.util.TimeUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.dto.tradingview.TradingQueryDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.vo.tradingview.TradingViewDataVo;
import com.mexc.match.engine.util.QueueKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/1/10 下午2:55.
 * 5分钟交易量统计
 */
public class TradingOrderFiveMinueSync {
    private static final Logger logger = LoggerFactory.getLogger("tradingViewTask");

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    @Autowired
    private MarketDelegate marketDelegate;

    public void sync() {
        logger.info("---5分钟交易数据开始同步---");
        Calendar now = Calendar.getInstance();
        Long currentTime = TimeUtil.getSecondTime(now.getTime());
        now.add(Calendar.MINUTE,-5);//减5分钟
        Long startTime = TimeUtil.getSecondTime(now.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startDate = new Date(startTime * 1000);
        Date endDate = new Date(currentTime * 1000);

        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);

        logger.info("---5分钟交易数据同步查询：startDate："+startDateStr+"-endDate："+endDateStr);

        List<MexcMarket> marketList = marketDelegate.queryMarketList();
        TradingQueryDto queryDto;
        TradingViewDataVo tradingViewData;
        if(!CollectionUtils.isEmpty(marketList)) {
            for (MexcMarket market : marketList) {
                List<MexcMarketVCoin> marketVCoinList = marketDelegate.queryMarketVcoinList(market.getId());
                if (!CollectionUtils.isEmpty(marketVCoinList)) {
                    for (MexcMarketVCoin vcoin : marketVCoinList) {
                        queryDto = new TradingQueryDto();
                        queryDto.setStartTime(startDate);
                        queryDto.setEndTime(endDate);
                        queryDto.setMarketId(market.getId());
                        queryDto.setVcoinId(vcoin.getVcoinId());
                        Map<String,BigDecimal> vhlPrice = mexcTradeDelegate.queryTradeVHLPrice(queryDto);
                        BigDecimal openPrice = mexcTradeDelegate.queryTradeOpenPrice(queryDto);
                        BigDecimal closePrice = mexcTradeDelegate.queryTradeClosePrice(queryDto);

                        tradingViewData = new TradingViewDataVo();
                        String key = TradingViewConstant.TRADING_5_PREFIX + QueueKeyUtil.getKey(market.getId(), vcoin.getVcoinId());
                        logger.info("---5分钟交易数据:"+vhlPrice+"-"+openPrice+"-"+closePrice);
                        if(vhlPrice != null) {
                            tradingViewData.setT(currentTime);
                            tradingViewData.setH(vhlPrice.get("h").doubleValue());
                            tradingViewData.setL(vhlPrice.get("l").doubleValue());
                            tradingViewData.setV(vhlPrice.get("v").doubleValue());
                            tradingViewData.setO(openPrice.doubleValue());
                            tradingViewData.setC(closePrice.doubleValue());
                            RedisUtil.zadd(key,currentTime, JSON.toJSONString(tradingViewData));
                            logger.info("---5分钟交易数据插入了redis，key:"+key);
                        }

                    }
                }
            }
        }
    }
}
