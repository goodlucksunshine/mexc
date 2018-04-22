package com.mexc.dao.dao.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.order.EntrustOrderQueryDto;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.dto.tradingview.TradingQueryDto;
import com.mexc.dao.vo.order.HistoryTradeOrderVo;
import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;
import com.mexc.dao.vo.vcoin.trade.Trade24HStatisticsVo;
import com.mexc.dao.vo.vcoin.trade.TradeOrderVo;
import com.mexc.dao.model.vcoin.MexcTrade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/11/22 下午4:47.
 */
@Repository
public interface MexcTradeDAO extends IBaseDAO<MexcTrade,String> {
    /**
     * 查询用户交易历史单
     * @param queryDto
     * @return
     */
    List<HistoryTradeOrderVo> queryTradeOrder(@Param("query") OrderQueryDto queryDto);

    List<TradeOrderVo> queryTradeOrderLimit(@Param("query")EntrustOrderQueryDto queryDto, @Param("limit") Integer limit);


    String query24HBeforePrice(@Param("marketId") String marketId,@Param("vcoinId") String vcoinId);

    LatestTradeVo queryLatestTrade(@Param("marketId") String marketId, @Param("vcoinId") String vcoinId);

    Trade24HStatisticsVo queryTrade24HStatistics(@Param("marketId") String marketId, @Param("vcoinId") String vcoinId);

    /**
     * 查询时间段内：最高价、最低价、成交价
     * @param queryDto
     * @return
     */
    Map<String,BigDecimal> queryTradeVHLPrice(@Param("query")TradingQueryDto queryDto);

    /**
     * 查询时间段内：开盘价
     * @param queryDto
     * @return
     */
    BigDecimal queryTradeOpenPrice(@Param("query") TradingQueryDto queryDto);

    /**
     * 查询时间段内收盘价
     * @param queryDto
     * @return
     */
    BigDecimal queryTradeClosePrice(@Param("query") TradingQueryDto queryDto);

    /**
     * 查询交易单列表
     * @param page
     * @param queryDto
     * @return
     */
    List<MexcTrade> queryTradeOrderPage(@Param("page") Page<MexcTrade> page, @Param("query") OrderQueryDto queryDto);
}
