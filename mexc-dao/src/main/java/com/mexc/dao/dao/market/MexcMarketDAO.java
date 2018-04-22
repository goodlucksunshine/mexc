package com.mexc.dao.dao.market;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.market.MexcMarket;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/22 下午4:34.
 */
@Repository
public interface MexcMarketDAO extends IBaseDAO<MexcMarket,String> {

    /**
     * 查询有币种的市场
     * @return
     */
    List<MexcMarket> queryMarketHasVCoin();
}
