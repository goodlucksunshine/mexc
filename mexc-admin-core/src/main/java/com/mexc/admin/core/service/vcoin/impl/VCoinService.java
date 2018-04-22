package com.mexc.admin.core.service.vcoin.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.vcoin.IVCoinService;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.market.MarketVCoinDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.vcoin.VCoinDto;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVcoinConfig;
import com.mexc.dao.vo.market.MarketVCoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mexc.common.constant.RedisKeyConstant.CONTRACT_LIB;

/**
 * Created by huangxinguang on 2017/11/23 下午2:22.
 */
@Service
public class VCoinService extends AbstractService<MexcVCoin, String> implements IVCoinService {

    @Autowired
    private VCoinDelegate vCoinDelegate;

    @Autowired
    private MarketVCoinDelegate marketVCoinDelegate;


    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcVCoin, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    public Page<MexcVCoin> queryVCoinListPage(Integer currentPage, Integer showCount, Integer status, String searchKey) {
        return vCoinDelegate.queryVCoinListPage(currentPage, showCount, status, searchKey);
    }

    @Override
    public void saveOrUpdate(VCoinDto vCoinDto) {
        vCoinDelegate.saveOrUpdate(vCoinDto);
        RedisUtil.sadd(CONTRACT_LIB, vCoinDto.getContractAddress().toLowerCase());
        List<MexcVCoin> mexcVCoins = vCoinDelegate.selectAll();

        if (mexcVCoins == null || mexcVCoins.size() == 0) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        for (MexcVcoinConfig mexcVCoin : mexcVCoins) {

            map.put(mexcVCoin.getId(), JSON.toJSONString(mexcVCoin));

        }
        RedisUtil.hmset("sys_dict", map);

    }


    @Override
    public List<MexcVCoin> queryMainVCoinList() {
        return vCoinDelegate.queryMainVCoinList();
    }

    @Override
    public List<MexcVCoin> queryAllVCoinList() {
        return vCoinDelegate.selectAll();
    }

    public void delete(String id) {
        vCoinDelegate.delete(id);
    }


    /**
     * 查询交易对
     *
     * @param marketVCoinId
     *
     * @return
     */
    public MarketVCoinVo queryMarketVCoin(String marketVCoinId) {
        return vCoinDelegate.queryMarketVCoin(marketVCoinId);
    }

    /**
     * 交易对列表
     *
     * @param currentPage
     * @param showCount
     * @param marketId
     * @param searchKey
     *
     * @return
     */
    @Override
    public Page<MarketVCoinVo> queryMarketVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey) {
        return vCoinDelegate.queryMarketVCoinList(currentPage, showCount, marketId, searchKey);
    }

    /**
     * 查询市场下非主币种的所有币种,且未关联
     *
     * @return
     */
    public List<MexcVCoin> queryMarketVCoinExcludeMainVCoin(String marketId) {
        return vCoinDelegate.queryMarketVCoinExcludeMainVCoin(marketId);
    }

    /**
     * 查询所有推荐的交易对
     *
     * @param currentPage
     * @param showCount
     * @param marketId
     * @param searchKey
     *
     * @return
     */
    @Override
    public Page<MarketVCoinVo> queryMarketSuggestVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey) {
        return vCoinDelegate.queryMarketSuggestVCoinList(currentPage, showCount, marketId, searchKey);
    }

    /**
     * 查询市场下，未推荐的币种
     *
     * @return
     */
    public List<MexcVCoin> queryMarketNoSuggestVCoinList(String marketId) {
        return vCoinDelegate.queryMarketNoSuggestVCoinList(marketId);
    }

    /**
     * 添加交易对
     *
     * @param marketVCoin
     *
     * @return
     */
    public int addMarketVCoin(MexcMarketVCoin marketVCoin) {
        return marketVCoinDelegate.insert(marketVCoin);
    }

    /**
     * 删除交易对
     *
     * @param marketVCoinId
     *
     * @return
     */
    public int deleteMarketVCoin(String marketVCoinId) {
        return marketVCoinDelegate.deleteByPrimaryKey(marketVCoinId);
    }

    /**
     * 更新交易对
     *
     * @param marketVCoin
     *
     * @return
     */
    public int updateMarketVCoin(MexcMarketVCoin marketVCoin) {
        return marketVCoinDelegate.updateByPrimaryKeySelective(marketVCoin);
    }

    public int updateMarketVCoinSuggest(MexcMarketVCoin marketVCoin) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where market_id=", marketVCoin.getMarketId());
        condition.put(" and vcoin_id=", marketVCoin.getVcoinId());
        return marketVCoinDelegate.updateParam(marketVCoin, condition);
    }
}
