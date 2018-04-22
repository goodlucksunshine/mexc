package com.mexc.api.vo.trade;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/5 下午7:32.
 */
public class TradeGroupResponse extends BaseResponse {

    private List<TradeGroupInfo> resultList;

    public List<TradeGroupInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<TradeGroupInfo> resultList) {
        this.resultList = resultList;
    }
}
