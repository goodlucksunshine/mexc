package com.mexc.web.core.service.order;

import com.mexc.common.base.ResultVo;
import com.mexc.dao.dto.order.*;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.tradingview.TradingViewDto;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetCashVcoin;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.order.HistoryTradeOrderVo;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    /**
     * 查询委托单
     * @param queryDto
     * @return
     */
    List<OrderDto> queryEntrustOrder(OrderQueryDto queryDto);

    /**
     * 查询委托单
     * @param queryDto
     * @param limit
     * @return
     */
    List<OrderDto> queryEntrustOrderLimit(OrderQueryDto queryDto, Integer limit);

    /**
     * 查询交易单
     * @param queryDto
     * @return
     */
    List<HistoryTradeOrderVo> queryTradeOrder(OrderQueryDto queryDto);

    /**
     * 自动委托交易
     * @param tradeDto
     */
    void entrustTrade(EntrustTradeDto tradeDto);

    /**
     * 手动交易
     * @param tradeDto
     */
    void handEntrustTrade(EntrustTradeDto tradeDto);

    /**
     * 撤销委托单
     * @param cancelEntrustTradeDto
     */
    void cancelEntrustTrade(CancelEntrustTradeDto cancelEntrustTradeDto);

    /**
     * 委托交易成功信息更新
     * @param entrustOrder 委托单
     * @param matchOrderList 撮合结果
     */
    void updateEntrustAndMatchOrderInfo(MatchOrder entrustOrder, List<MatchOrder> matchOrderList);

    /**
     * 获取交易数据
     * @param tradingViewDto
     * @return
     */
    Map<String,Object> getTradingData(TradingViewDto tradingViewDto);

    /**
     * 充值历史记录
     * @param memberId
     * @return
     */
    List<MexcAssetRecharge> queryAssetRechargeList(String memberId);

    /**
     * 提现历史记录
     * @param memberId
     * @return
     */
    List<MexcAssetCash> queryAssetCashList(String memberId);


    List<MexcAssetCashVcoin> queryAssetCashVcoinList(String memberId);

    MexcEnTrust queryEntrust(String tradeNo);

    void doMatchAndUpdate(MatchOrder entrustOrder,ResultVo<List<MatchOrder>> resultVo);
}
