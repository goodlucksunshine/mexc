package com.mexc.api.vo.coin;

/**
 * Created by huangxinguang on 2018/2/6 下午2:42.
 * 币种信息
 */
public class CoinInfo {
    /**
     * 币种ID
     */
    private String currencyId;

    /**
     * 简称
     */
    private String currencyShortName;
    /**
     * 全称
     */
    private String currencyFullName;

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyShortName() {
        return currencyShortName;
    }

    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }

    public String getCurrencyFullName() {
        return currencyFullName;
    }

    public void setCurrencyFullName(String currencyFullName) {
        this.currencyFullName = currencyFullName;
    }
}
