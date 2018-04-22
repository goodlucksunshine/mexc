package com.mexc.dao.delegate.market;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.market.MexcMarketVCoinDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.market.MexcMarketVCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/12 下午3:43.
 */
@Component
@Transactional
public class MarketVCoinDelegate extends AbstractDelegate<MexcMarketVCoin,String> {

    @Autowired
    private MexcMarketVCoinDAO marketVCoinDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMarketVCoin, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public MexcMarketVCoin queryMarketVCoin(String marketId,String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_id=",marketId);
        condition.put(" and vcoin_id=",vcoinId);
        List<MexcMarketVCoin> list = marketVCoinDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
