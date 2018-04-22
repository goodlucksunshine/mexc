package com.mexc.web.core.model.request;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/31
 * Time: 下午3:24
 */
public class MemberAssetCashRequest {
    /**
     * 用户ID
     */
    private String account;
    /**
     * 提现资产
     */
    private String assetId;
    /**
     * 提现数量
     */
    private String cashValue;
    /**
     * 提现地址
     */
    private String cashAddress;
    /**
     * 标签
     */
    private String addressTab;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCashValue() {
        return cashValue;
    }

    public void setCashValue(String cashValue) {
        this.cashValue = cashValue;
    }

    public String getCashAddress() {
        return cashAddress;
    }

    public void setCashAddress(String cashAddress) {
        this.cashAddress = cashAddress;
    }

    public String getAddressTab() {
        return addressTab;
    }

    public void setAddressTab(String addressTab) {
        this.addressTab = addressTab;
    }
}
