package com.mexc.api.vo.coin;

import com.mexc.api.vo.BaseResponse;

import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 下午2:44.
 */
public class CoinResponse extends BaseResponse {
    private List<CoinInfo> resultList;

    public List<CoinInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<CoinInfo> resultList) {
        this.resultList = resultList;
    }
}
