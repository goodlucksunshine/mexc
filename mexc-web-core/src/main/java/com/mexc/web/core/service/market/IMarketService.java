package com.mexc.web.core.service.market;

import com.mexc.dao.model.market.MexcMarket;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/7 上午11:54.
 */
public interface IMarketService {

    MexcMarket queryMarketById(String marketId);

    List<MexcMarket> queryMarketList();

    MexcMarket queryMarketHasVCoin();

}
