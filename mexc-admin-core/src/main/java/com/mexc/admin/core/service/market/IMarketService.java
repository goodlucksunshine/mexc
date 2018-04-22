package com.mexc.admin.core.service.market;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.model.market.MexcMarket;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/23 下午4:53.
 */
public interface IMarketService extends IBaseService<MexcMarket,String> {

    Page<MexcMarket> queryMarketListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(MexcMarket market);

    void delete(String marketId);

    List<MexcMarket> queryMarketList();
}
