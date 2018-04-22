package com.mexc.dao.dto.asset;

/**
 * Created by huangxinguang on 2018/1/22 下午5:57.
 * 提现查询
 */
public class AssetCashDto {
    private Integer currentPage;

    private Integer showCount;

    private String account;
    /**
     * 币种
     */
    private String txApplyToken;

    /**
     * 提现状态 0:提现待确认 -1:提现失败 1:提现成功 2:处理中
     */
    private Integer cashStatus;

    private String startTime;

    private String endTime;

    private String searchMethod;

    private String month;

    public String getMonth() {return month;}

    public void setMonth(String month) {this.month = month;}

    public String getSearchMethod() {return searchMethod;}

    public void setSearchMethod(String searchMethod) {this.searchMethod = searchMethod;}

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

    public String getTxApplyToken() {
        return txApplyToken;
    }

    public void setTxApplyToken(String txApplyToken) {
        this.txApplyToken = txApplyToken;
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
