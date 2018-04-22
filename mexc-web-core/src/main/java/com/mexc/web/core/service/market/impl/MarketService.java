package com.mexc.web.core.service.market.impl;

import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.web.core.service.market.IMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/7 上午11:54.
 */
@Service
public class MarketService implements IMarketService {

    @Autowired
    private MarketDelegate marketDelegate;

    @Override
    public MexcMarket queryMarketById(String marketId) {
        return marketDelegate.selectByPrimaryKey(marketId);
    }

    @Override
    public List<MexcMarket> queryMarketList() {
        return marketDelegate.queryMarketList();
    }

    public MexcMarket queryMarketHasVCoin() {
        return marketDelegate.queryMarketHasVCoin();
    }
}
