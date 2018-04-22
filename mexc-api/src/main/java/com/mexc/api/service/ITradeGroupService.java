package com.mexc.api.service;

import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.trade.CollectRequest;
import com.mexc.api.vo.trade.TradeGroupRequest;
import com.mexc.api.vo.trade.TradeGroupResponse;

/**
 * Created by huangxinguang on 2018/2/5 下午7:28.
 */
public interface ITradeGroupService {
    /**
     * 获取所有交易对
     * @param tradeGroupRequest
     * @return
     */
    TradeGroupResponse getTradeGroup(TradeGroupRequest tradeGroupRequest);

    /**
     * 添加自选或删除自选
     * @param collectRequest
     * @return
     */
    BaseResponse addOrCancelCollect(CollectRequest collectRequest);
}
