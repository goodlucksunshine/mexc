package com.mexc.dao.delegate.market;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.market.MexcMarketDAO;
import com.mexc.dao.dao.market.MexcMarketVCoinDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by huangxinguang on 2017/11/23 下午4:55.
 */
@Component
@Transactional
public class MarketDelegate extends AbstractDelegate<MexcMarket,String> {

    @Autowired
    private MexcMarketDAO mexcMarketDAO;

    @Autowired
    private MexcMarketVCoinDAO mexcMarketVCoinDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMarket, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcMarket> queryMarketListPage(Integer currentPage, Integer showCount, String searchKey) {
        Page<MexcMarket> page = new Page<>(currentPage,showCount);
        SqlCondition condition = new SqlCondition();
        if(StringUtils.isNotEmpty(searchKey)) {
            condition.put(" where market_name like","%"+searchKey+"%");
        }
        mexcMarketDAO.selectPagination(page,condition);
        return page;
    }

    public void saveOrUpdate(MexcMarket market) {

        if(!StringUtils.isEmpty(market.getId())) {
            mexcMarketDAO.updateByPrimaryKeySelective(market);
        }else {
            mexcMarketDAO.insert(market);
        }
    }

    /**
     * 查询所有 开放的市场
     * @return
     */
    public List<MexcMarket> queryMarketList() {
        SqlCondition condition = new SqlCondition();
        condition.put(" where status =",1);
        return mexcMarketDAO.selectByCondition(condition);
    }

    public void delete(String marketId) {
        mexcMarketDAO.deleteByPrimaryKey(marketId);
        //删除关联
        SqlCondition condition = new SqlCondition();
        condition.put("where market_id=",marketId);
        mexcMarketVCoinDAO.deleteByParam(condition);
    }

    public List<MexcMarketVCoin> queryMarketVcoinList(String marketId) {
        SqlCondition condition = new SqlCondition();
        condition.put("where market_id=",marketId);
        return mexcMarketVCoinDAO.selectByCondition(condition);
    }

    public Boolean isSuggest(String marketId,String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_id=",marketId);
        condition.put(" and vcoin_id=",vcoinId);
        List<MexcMarketVCoin> list = mexcMarketVCoinDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(list)) {
            return list.get(0).getSuggest() == 1 ? true : false;
        }
        return true;
    }

    public MexcMarket queryMarketByName(String marketName) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_name=",marketName);
        List<MexcMarket> list = mexcMarketDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public MexcMarket queryMarketHasVCoin() {
        List<MexcMarket> list = mexcMarketDAO.queryMarketHasVCoin();
        if(CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
