package com.mexc.admin.core.service.market.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.market.IMarketService;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.model.market.MexcMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/23 下午4:53.
 */
@Service
public class MarketService extends AbstractService<MexcMarket,String> implements IMarketService {

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcMarket, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    public Page<MexcMarket> queryMarketListPage(Integer currentPage,Integer showCount,String searchKey) {
        return marketDelegate.queryMarketListPage(currentPage,showCount,searchKey);
    }

    public List<MexcMarket> queryMarketList() {
        return marketDelegate.queryMarketList();
    }

    @Override
    public void saveOrUpdate(MexcMarket market) {
        marketDelegate.saveOrUpdate(market);
    }

    @Override
    public void delete(String marketId) {
        marketDelegate.delete(marketId);
    }
}
