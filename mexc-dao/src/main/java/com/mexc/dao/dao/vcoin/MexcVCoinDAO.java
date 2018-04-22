package com.mexc.dao.dao.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.vo.market.MarketVCoinVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/22 下午4:48.
 */
@Repository
public interface MexcVCoinDAO extends IBaseDAO<MexcVCoin,String> {
    /**
     * 查询币种
     * @param marketId
     * @return
     */
    List<MexcVCoin> queryMarketVCoinList(@Param("marketId") String marketId);

    /**
     * 查询市场下未推荐的币种
     * @param marketId
     * @return
     */
    List<MexcVCoin> queryMarketNoSuggestVCoinList(@Param("marketId") String marketId);

    /**
     * 交易对列表
     * @param page
     * @param marketId
     * @param searchKey
     * @return
     */
    List<MarketVCoinVo> queryMarketVCoinListPage(@Param("page") Page<MarketVCoinVo> page, @Param("marketId")String marketId, @Param("searchKey") String searchKey);

    /**
     * 推荐交易对列表
     * @param page
     * @param marketId
     * @param searchKey
     * @return
     */
    List<MarketVCoinVo> queryMarketSuggestVCoinListPage(@Param("page") Page<MarketVCoinVo> page, @Param("marketId")String marketId, @Param("searchKey") String searchKey);

    /**
     * 查询主币之外的所有币种,且未被市场关联的币种
     * @param marketId
     * @return
     */
    List<MexcVCoin> queryMarketVCoinExcludeMainVCoin(String marketId);

    /**
     * 查询交易对
     * @param marketVCoinId
     * @return
     */
    MarketVCoinVo queryMarketVCoin(@Param("marketVCoinId") String marketVCoinId);
}
