package com.mexc.api.vo.order;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 下午3:01.
 */
public class DealHisResponse extends BaseResponse {

    private List<DealHisInfo> resultList;

    public List<DealHisInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<DealHisInfo> resultList) {
        this.resultList = resultList;
    }
}
