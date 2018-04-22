package com.mexc.task.quartz.trade;

import com.alibaba.fastjson.JSON;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.vcoin.BtcValueDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;
import com.mexc.dao.vo.vcoin.trade.Trade24HStatisticsVo;
import com.mexc.dao.vo.vcoin.trade.Trade24HUpDownVo;
import com.mexc.dao.vo.vcoin.trade.TradeGroupInfoVo;
import com.mexc.match.engine.util.MatchExchangeUtil;
import com.mexc.match.engine.util.QueueKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangxinguang on 2018/1/20 下午4:35.
 * 交易对行情同步
 */
public class TradeGroupMarketCapSync {

    private static final Logger logger = LoggerFactory.getLogger(MarketAndVCoinSync.class);

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private VCoinDelegate vCoinDelegate;

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    public void sync() {
        List<MexcMarket> marketList = marketDelegate.queryMarketList();

        //交易对行情
        try {
            if(!CollectionUtils.isEmpty(marketList)) {
                logger.info("同步所有交易对行情:{}", JSON.toJSONString(marketList));
                for (MexcMarket market : marketList) {
                    List<MexcMarketVCoin> marketVCoinList = marketDelegate.queryMarketVcoinList(market.getId());
                    if(!CollectionUtils.isEmpty(marketVCoinList)) {
                        for (MexcMarketVCoin marketVCoin : marketVCoinList) {

                                MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(marketVCoin.getVcoinId());

                                TradeGroupInfoVo tradeGroupInfo = new TradeGroupInfoVo();//交易对

                                String key = QueueKeyUtil.getKey(market.getId(), marketVCoin.getVcoinId());

                                //最新交易
                                LatestTradeVo latestTradeVo = mexcTradeDelegate.queryLatestTrade(market.getId(), marketVCoin.getVcoinId());

                                //24小时涨跌
                                Trade24HUpDownVo trade24HUpDownVo = new Trade24HUpDownVo();
                                String before24Price = mexcTradeDelegate.query24HBeforePrice(market.getId(), marketVCoin.getVcoinId());
                                if (latestTradeVo == null) {
                                    latestTradeVo = new LatestTradeVo();
                                }
                                if (before24Price == null) {
                                    before24Price = "0";
                                }

                                String upOrDownStr = "0";
                                BigDecimal upOrDown = new BigDecimal(latestTradeVo.getTradePrice()).subtract(new BigDecimal(before24Price));
                                if (upOrDown.compareTo(new BigDecimal(0)) > 0) {
                                    upOrDownStr = "<i class='col-green'>+" + upOrDown.setScale(8).toPlainString() + "</i>";
                                } else if (upOrDown.compareTo(new BigDecimal(0)) < 0) {
                                    upOrDownStr = "<i class='col-red'>" + upOrDown.setScale(8).toPlainString() + "</i>";
                                }

                                String upOrDownRateStr = "0.0%";
                                BigDecimal upOrDwonRate = new BigDecimal(0);
                                if (!before24Price.equals("0")) {
                                    upOrDwonRate = upOrDown.divide(new BigDecimal(before24Price), 2, BigDecimal.ROUND_HALF_UP);
                                }
                                if (upOrDwonRate.compareTo(new BigDecimal(0)) > 0) {
                                    upOrDownRateStr = "<i class='col-green'>+" + (upOrDwonRate.multiply(new BigDecimal(100))).setScale(2).toPlainString() + "%</i>";
                                } else if (upOrDwonRate.compareTo(new BigDecimal(0)) < 0) {
                                    upOrDownRateStr = "<i class='col-red'>" + (upOrDwonRate.multiply(new BigDecimal(100))).setScale(2).toPlainString() + "%</i>";
                                }
                                trade24HUpDownVo.setUpOrDown(upOrDownStr);
                                trade24HUpDownVo.setUpOrDownRate(upOrDownRateStr);

                                //24小时统计
                                Trade24HStatisticsVo trade24HStatisticsVo = mexcTradeDelegate.queryTrade24HStatistics(market.getId(), marketVCoin.getVcoinId());

                                //推荐
                                Boolean suggest = marketDelegate.isSuggest(market.getId(), marketVCoin.getVcoinId());
                                if (suggest == null) {
                                    suggest = false;
                                }

                                tradeGroupInfo.setMarketName(market.getMarketName());
                                tradeGroupInfo.setVcoinNameEn(vCoin.getVcoinNameEn());
                                tradeGroupInfo.setVcoinIcon(vCoin.getIcon());
                                tradeGroupInfo.setLatestTradePrice(latestTradeVo.getTradePrice());
                                tradeGroupInfo.setMarketId(market.getId());
                                tradeGroupInfo.setVcoinId(marketVCoin.getVcoinId());
                                tradeGroupInfo.setSuggest(suggest ? 1 : 0);
                                if (trade24HStatisticsVo != null) {
                                    tradeGroupInfo.setMaxTradePrice(trade24HStatisticsVo.getMaxTradePrice());
                                    tradeGroupInfo.setMinTradePrice(trade24HStatisticsVo.getMinTradePrice());
                                    tradeGroupInfo.setSumTradePrice(trade24HStatisticsVo.getSumTradePrice());
                                }
                                tradeGroupInfo.setUpOrDownRateValue(upOrDown.toPlainString());
                                tradeGroupInfo.setUpOrDown(upOrDownStr);
                                tradeGroupInfo.setUpOrDownRate(upOrDownRateStr);
                                tradeGroupInfo.setUpOrDownRateValue(upOrDwonRate.toPlainString());

                                //从redis获取美元估值 * 最新价
                                String usdKey = vCoin.getVcoinNameEn()+"-"+vCoin.getId();
                                String btcValueJson = RedisUtil.get(usdKey);
                                if(!StringUtils.isEmpty(btcValueJson)) {
                                    BtcValueDto valueDto = JSON.parseObject(btcValueJson, BtcValueDto.class);
                                    BigDecimal latestPrice = new BigDecimal(latestTradeVo.getTradePrice());
                                    BigDecimal usdValueUnit = new BigDecimal(valueDto.getPriceBtc()).multiply(new BigDecimal(valueDto.getPriceUsb()));
                                    BigDecimal btcValue = new BigDecimal(valueDto.getPriceBtc()).multiply(latestPrice);//btc估值
                                    BigDecimal usdValue = usdValueUnit.multiply(latestPrice);//美元估值
                                    tradeGroupInfo.setUsdValue(usdValue.toPlainString());
                                    tradeGroupInfo.setBtcValue(btcValue.toPlainString());
                                }

                                logger.info("同步所有交易对行情 存放redis key:" + CommonConstant.TRADE_GROUP + key + ",value" + JSON.toJSONString(tradeGroupInfo));
                                RedisUtil.set(CommonConstant.TRADE_GROUP + key, JSON.toJSONString(tradeGroupInfo));
                                RedisUtil.set(MatchExchangeUtil.TRADED_PRICE_PREFIX + key,latestTradeVo.getTradePrice());//最新成交价

                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("同步行情异常",e);
        }
    }
}
