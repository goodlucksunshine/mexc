package com.mexc.dao.delegate.market;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.market.MexcMarketVCoinCollectDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.market.MexcMarketVcoinCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/11 下午1:29.
 */
@Component
@Transactional
public class MarketVCoinCollectDelegate extends AbstractDelegate<MexcMarketVcoinCollect,String> {

    @Autowired
    private MexcMarketVCoinCollectDAO mexcMarketVCoinCollectDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMarketVcoinCollect, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public int uncollect(MexcMarketVcoinCollect collect) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_id=",collect.getMarketId());
        condition.put(" and vcoin_id=",collect.getVcoinId());
        condition.put(" and create_by=",collect.getCreateBy());
        return mexcMarketVCoinCollectDAO.deleteByParam(condition);
    }

    public boolean checkCollect(String marketId,String vcoinId,String memberId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_id =",marketId);
        condition.put(" and vcoin_id=",vcoinId);
        condition.put(" and create_by=",memberId);
        List<MexcMarketVcoinCollect> list = mexcMarketVCoinCollectDAO.selectByCondition(condition);
        return CollectionUtils.isEmpty(list) ? false : true;
    }
}
