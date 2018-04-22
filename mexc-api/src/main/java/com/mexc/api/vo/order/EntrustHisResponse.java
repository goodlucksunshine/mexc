package com.mexc.api.vo.order;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 下午3:39.
 */
public class EntrustHisResponse extends BaseResponse {

    private List<EntrustInfo> resultList;

    public List<EntrustInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<EntrustInfo> resultList) {
        this.resultList = resultList;
    }
}
