package com.mexc.api.service;

import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.order.*;

/**
 * Created by huangxinguang on 2018/2/6 上午11:35.
 */
public interface IOrderService {
    /**
     * 成交历史
     * @param dealHisRequest
     * @return
     */
    DealHisResponse getDealHis(DealHisRequest dealHisRequest);

    /**
     * 委托历史
     * @param entrustRequest
     * @return
     */
    EntrustHisResponse getEntrustHis(EntrustHisRequest entrustRequest);

    /**
     * 委托交易
     * @param entrustRequest
     * @return
     */
    BaseResponse doEntrust(EntrustRequest entrustRequest);

    /**
     * 撤单
     * @param entrustCancelRequest
     * @return
     */
    BaseResponse doCancel(EntrustCancelRequest entrustCancelRequest);
}
