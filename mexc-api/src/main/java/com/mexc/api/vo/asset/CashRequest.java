package com.mexc.api.vo.asset;

/**
 * Created by huangxinguang on 2018/2/6 下午1:57.
 */
public class CashRequest {
    private String userId;

    /**
     * 提现币种
     */
    private String txCurrencyId;
    /**
     * 提现地址
     */
    private String txAddress;

    /**
     * 标签
     */
    private String txAddressTab;
    /**
     * 提现数量
     */
    private String txCount;
    /**
     * 邮箱验证码
     */
    private String authCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTxCurrencyId() {
        return txCurrencyId;
    }

    public void setTxCurrencyId(String txCurrencyId) {
        this.txCurrencyId = txCurrencyId;
    }

    public String getTxAddress() {
        return txAddress;
    }

    public void setTxAddress(String txAddress) {
        this.txAddress = txAddress;
    }

    public String getTxCount() {
        return txCount;
    }

    public void setTxCount(String txCount) {
        this.txCount = txCount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTxAddressTab() {
        return txAddressTab;
    }

    public void setTxAddressTab(String txAddressTab) {
        this.txAddressTab = txAddressTab;
    }
}
