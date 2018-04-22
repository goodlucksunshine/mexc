package com.mexc.dao.dto.asset;

/**
 * Created by huangxinguang on 2018/1/22 下午5:04.
 * 充值查询
 */
public class AssetRechargeDto {
    private Integer currentPage;

    private Integer showCount;

    /**
     * 账户
     */
    private String account;
    /**
     * 币种TOKEN
     */
    private String txToken;

    private String startTime;

    private String endTime;

    private String searchMethod;

    private String month;

    public String getSearchMethod() {return searchMethod;}

    public void setSearchMethod(String searchMethod) {this.searchMethod = searchMethod;}

    public String getMonth() {return month;}

    public void setMonth(String month) {this.month = month;}

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTxToken() {
        return txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
