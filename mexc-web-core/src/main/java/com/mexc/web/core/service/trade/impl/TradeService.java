package com.mexc.web.core.service.trade.impl;

import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;
import com.mexc.web.core.service.trade.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2018/1/14 下午8:06.
 */
@Service
public class TradeService implements ITradeService {

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    public LatestTradeVo queryLatestTrade(String marketId,String vcoinId) {
        return mexcTradeDelegate.queryLatestTrade(marketId,vcoinId);
    }
}
