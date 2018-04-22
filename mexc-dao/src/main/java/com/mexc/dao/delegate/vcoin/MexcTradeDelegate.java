package com.mexc.dao.delegate.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.vcoin.MexcTradeDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.order.EntrustOrderQueryDto;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.dto.tradingview.TradingQueryDto;
import com.mexc.dao.vo.order.HistoryTradeOrderVo;
import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;
import com.mexc.dao.vo.vcoin.trade.Trade24HStatisticsVo;
import com.mexc.dao.vo.vcoin.trade.TradeOrderVo;
import com.mexc.dao.model.vcoin.MexcTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/12/20 下午3:59.
 */
@Component
@Transactional
public class MexcTradeDelegate extends AbstractDelegate<MexcTrade,String> {

    @Autowired
    private MexcTradeDAO mexcTradeDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcTrade, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public List<HistoryTradeOrderVo> queryTradeOrder(OrderQueryDto queryDto) {
        return mexcTradeDAO.queryTradeOrder(queryDto);
    }

    public List<TradeOrderVo> queryTradeOrderLimit(EntrustOrderQueryDto queryDto, Integer limit) {
        return mexcTradeDAO.queryTradeOrderLimit(queryDto,limit);
    }

    public String query24HBeforePrice(String marketId,String vcoinId) {
        return mexcTradeDAO.query24HBeforePrice(marketId,vcoinId);
    }

    public LatestTradeVo queryLatestTrade(String marketId, String vcoinId) {
        return mexcTradeDAO.queryLatestTrade(marketId,vcoinId);
    }

    public Trade24HStatisticsVo queryTrade24HStatistics(String marketId, String vcoinId) {
        return mexcTradeDAO.queryTrade24HStatistics(marketId,vcoinId);
    }

    public Map<String, BigDecimal> queryTradeVHLPrice(TradingQueryDto queryDto) {
        return mexcTradeDAO.queryTradeVHLPrice(queryDto);
    }

    public BigDecimal queryTradeOpenPrice(TradingQueryDto queryDto) {
        return mexcTradeDAO.queryTradeOpenPrice(queryDto);
    }

    public BigDecimal queryTradeClosePrice(TradingQueryDto queryDto) {
        return mexcTradeDAO.queryTradeClosePrice(queryDto);
    }

    public Page<MexcTrade> queryTradeOrderPage(OrderQueryDto queryDto) {
        Page<MexcTrade> page = new Page<>(queryDto.getCurrentPage(),queryDto.getShowCount());
        List<MexcTrade> list = mexcTradeDAO.queryTradeOrderPage(page,queryDto);
        page.setResultList(list);
        return page;
    }
}
