package com.mexc.api.vo.asset;

/**
 * Created by huangxinguang on 2018/2/6 上午11:41.
 */
public class RechargeAddressRequest {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 币种ID
     */
    private String currencyId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
}
