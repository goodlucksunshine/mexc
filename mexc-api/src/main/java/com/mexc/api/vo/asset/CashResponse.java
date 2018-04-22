package com.mexc.api.vo.asset;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 上午10:27.
 */
public class CashResponse extends BaseResponse {

    private List<CashRecord> resultList;

    public List<CashRecord> getResultList() {
        return resultList;
    }

    public void setResultList(List<CashRecord> resultList) {
        this.resultList = resultList;
    }
}
