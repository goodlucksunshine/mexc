package com.mexc.task.quartz.trade;

import com.alibaba.fastjson.JSON;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.match.engine.util.QueueKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangxinguang on 2018/1/20 下午4:35.
 * 市场和币种同步
 */
public class MarketAndVCoinSync {

    private static final Logger logger= LoggerFactory.getLogger(MarketAndVCoinSync.class);

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private VCoinDelegate vCoinDelegate;


    public void sync() {
        List<MexcMarket> marketList = marketDelegate.queryMarketList();
        List<MexcVCoin> vCoinList = null;

        try {
            if (!CollectionUtils.isEmpty(marketList)) {
                logger.info("同步市场和币种 存放redis key:" + CommonConstant.MARKET_LIST + ",value" + JSON.toJSONString(marketList));
                RedisUtil.set(CommonConstant.MARKET_LIST, JSON.toJSONString(marketList));

                for (MexcMarket market : marketList) {
                    List<MexcMarketVCoin> marketVCoinList = marketDelegate.queryMarketVcoinList(market.getId());
                    if (!CollectionUtils.isEmpty(marketVCoinList)) {
                        vCoinList = new LinkedList<>();
                        for (MexcMarketVCoin marketVCoin : marketVCoinList) {
                            MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(marketVCoin.getVcoinId());
                            vCoinList.add(vCoin);
                        }
                    }
                    if (!CollectionUtils.isEmpty(vCoinList)) {
                        String key = QueueKeyUtil.getKey(market.getId());
                        logger.info("同步市场和币种2 存放redis key:" + CommonConstant.MARKET_VCOIN_LIST_PREFIX + key + ",value" + JSON.toJSONString(vCoinList));
                        RedisUtil.set(CommonConstant.MARKET_VCOIN_LIST_PREFIX + key, JSON.toJSONString(vCoinList));
                    }
                }
            }
        }catch (Exception e) {
            logger.error("同步市场和币种异常",e);
        }
    }


}
