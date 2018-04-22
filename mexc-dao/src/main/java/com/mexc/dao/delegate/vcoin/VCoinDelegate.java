package com.mexc.dao.delegate.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.market.MexcMarketVCoinDAO;
import com.mexc.dao.dao.vcoin.MexcVCoinDAO;
import com.mexc.dao.dao.vcoin.MexcVCoinFeeDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.plat.PlatAssetDelegate;
import com.mexc.dao.dto.vcoin.VCoinDto;
import com.mexc.dao.model.plat.MexcPlatAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import com.mexc.dao.vo.market.MarketVCoinVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by huangxinguang on 2017/11/23 下午2:26.
 */
@Component
@Transactional
public class VCoinDelegate extends AbstractDelegate<MexcVCoin, String> {

    @Autowired
    private MexcVCoinDAO mexcVCoinDAO;

    @Autowired
    private MexcVCoinFeeDAO mexcVCoinFeeDAO;

    @Autowired
    private MexcMarketVCoinDAO mexcMarketVCoinDAO;

    @Autowired
    private PlatAssetDelegate platAssetDelegate;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcVCoin, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcVCoin> queryVCoinListPage(Integer currentPage, Integer showCount, Integer status, String searchKey) {
        Page<MexcVCoin> page = new Page<>(currentPage, showCount);
        SqlCondition condition = new SqlCondition();
        condition.put(" where 1=", 1);
        if (status != null) {
            condition.put(" and status=", status);
        }
        if (!StringUtils.isEmpty(searchKey)) {
            condition.put(" and vcoin_name like ", "%" + searchKey + "%");
        }
        mexcVCoinDAO.selectPagination(page, condition);
        return page;
    }

    public List<MexcVCoin> queryCanUseVcoinList() {
        SqlCondition condition = new SqlCondition();
        condition.put(" where status=", 1);
        return selectByCondition(condition);
    }

    public List<MexcVCoin> queryMainCoinList()
    {
        SqlCondition condition = new SqlCondition();
        condition.put(" where main_coin=", 1);
        return selectByCondition(condition);
    }


    public MexcVCoinFee queryVCoinFee(String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where vcoin_id=", vcoinId);
        List<MexcVCoinFee> list = mexcVCoinFeeDAO.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    public void saveOrUpdate(VCoinDto vCoinDto) {
        MexcVCoin vCoin = new MexcVCoin();
        BeanUtils.copyProperties(vCoinDto, vCoin);

        MexcVCoinFee vCoinFee = new MexcVCoinFee();
        BeanUtils.copyProperties(vCoinDto, vCoinFee);

        if (StringUtils.isEmpty(vCoinDto.getId())) {
            String id = UUIDUtil.get32UUID();
            vCoin.setId(id);
            mexcVCoinDAO.insert(vCoin);
            vCoinFee.setVcoinId(vCoin.getId());
            mexcVCoinFeeDAO.insert(vCoinFee);
            MexcPlatAsset mexcPlatAsset = new MexcPlatAsset();
            mexcPlatAsset.setId(UUIDUtil.get32UUID());
            mexcPlatAsset.setVcoinId(vCoin.getId());
            mexcPlatAsset.setBalanceAmount(BigDecimal.ZERO);
            mexcPlatAsset.setTotalAmount(BigDecimal.ZERO);
            mexcPlatAsset.setFrozenAmount(BigDecimal.ZERO);
            mexcPlatAsset.setCreateBy("system");
            mexcPlatAsset.setCreateTime(new Date());
            platAssetDelegate.insertSelective(mexcPlatAsset);
        } else {
            mexcVCoinDAO.updateByPrimaryKeySelective(vCoin);
            MexcVCoinFee tempVCoinFee = new MexcVCoinFee();
            tempVCoinFee.setVcoinId(vCoinDto.getId());
            MexcVCoinFee oldVCoinFee = mexcVCoinFeeDAO.selectOne(tempVCoinFee);
            vCoinFee.setId(oldVCoinFee.getId());
            mexcVCoinFeeDAO.updateByPrimaryKeySelective(vCoinFee);
        }
    }


    public List<MexcVCoin> queryContract() {
        return mexcVCoinDAO.selectAll();
    }

    public List<MexcVCoin> queryMainVCoinList() {
        SqlCondition condition = new SqlCondition();
        condition.put(" where main_coin=", 1);
        return mexcVCoinDAO.selectByCondition(condition);
    }

    public MexcVCoin queryVCoinByNameEn(String vcoinNameEn) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where vcoin_name_en=", vcoinNameEn);
        List<MexcVCoin> list = mexcVCoinDAO.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public void delete(String id) {
        mexcVCoinDAO.deleteByPrimaryKey(id);

        //删除费率
        SqlCondition condition = new SqlCondition();
        condition.put(" where vcoin_id=", id);
        mexcVCoinFeeDAO.deleteByParam(condition);

        //删除与市场的关联
        condition = new SqlCondition();
        condition.put("where vcoin_id=", id);
        mexcMarketVCoinDAO.deleteByParam(condition);
    }

    public Page<MarketVCoinVo> queryMarketVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey) {
        Page<MarketVCoinVo> page = new Page<>(currentPage, showCount);
        List<MarketVCoinVo> list = mexcVCoinDAO.queryMarketVCoinListPage(page, marketId, searchKey);
        page.setResultList(list);
        return page;
    }

    public List<MexcVCoin> queryMarketNoSuggestVCoinList(String marketId) {
        return mexcVCoinDAO.queryMarketNoSuggestVCoinList(marketId);
    }

    public Page<MarketVCoinVo> queryMarketSuggestVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey) {
        Page<MarketVCoinVo> page = new Page<>(currentPage, showCount);
        List<MarketVCoinVo> list = mexcVCoinDAO.queryMarketSuggestVCoinListPage(page, marketId, searchKey);
        page.setResultList(list);
        return page;
    }

    public MarketVCoinVo queryMarketVCoin(String marketVCoinId) {
        return mexcVCoinDAO.queryMarketVCoin(marketVCoinId);
    }


    public List<MexcVCoin> queryMarketVCoinExcludeMainVCoin(String marketId) {
        return mexcVCoinDAO.queryMarketVCoinExcludeMainVCoin(marketId);
    }

    public MexcVCoin queryByEth() {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where vcoin_token=", "ETH");
        sqlCondition.put(" and main_coin=", 1);
        List<MexcVCoin> vCoins = mexcVCoinDAO.selectByCondition(sqlCondition);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(vCoins)) {
            return vCoins.get(0);
        } else {
            return null;
        }
    }


    public MexcVCoin queryByContract(String contract) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where contract_address=", contract);
        List<MexcVCoin> vCoins = mexcVCoinDAO.selectByCondition(sqlCondition);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(vCoins)) {
            return vCoins.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询市场下默认币种
     *
     * @param marketId
     *
     * @return
     */
    public MexcVCoin queryDefaultVCoin(String marketId) {
        List<MexcVCoin> list = mexcVCoinDAO.queryMarketVCoinList(marketId);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
