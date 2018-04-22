package com.mexc.api.vo.asset;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 上午11:07.
 */
public class RechargeResponse extends BaseResponse {

    private List<RechargeRecord> resultList;

    public List<RechargeRecord> getResultList() {
        return resultList;
    }

    public void setResultList(List<RechargeRecord> resultList) {
        this.resultList = resultList;
    }
}
