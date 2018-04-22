package com.mexc.web.core.service.trade;

import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;

/**
 * Created by huangxinguang on 2018/1/14 下午8:05.
 */
public interface ITradeService {

    LatestTradeVo queryLatestTrade(String marketId, String vcoinId);
}
