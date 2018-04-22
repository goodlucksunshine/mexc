package com.mexc.admin.core.service.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.dto.vcoin.VCoinDto;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.vo.market.MarketVCoinVo;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/23 下午2:22.
 */
public interface IVCoinService extends IBaseService<MexcVCoin,String>{

    Page<MexcVCoin> queryVCoinListPage(Integer currentPage, Integer showCount, Integer status, String searchKey);

    void saveOrUpdate(VCoinDto vCoinDto);

    void delete(String id);

    /**
     * 查询所有主币
     * @return
     */
    List<MexcVCoin> queryMainVCoinList();

    List<MexcVCoin> queryAllVCoinList();

    /**
     * 查询市场下的所有币种
     * @param currentPage
     * @param showCount
     * @param searchKey
     * @param marketId
     * @return
     */
    Page<MarketVCoinVo> queryMarketVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey);


    Page<MarketVCoinVo> queryMarketSuggestVCoinList(Integer currentPage, Integer showCount, String marketId, String searchKey);


    MarketVCoinVo queryMarketVCoin(String marketVCoinId);
}
