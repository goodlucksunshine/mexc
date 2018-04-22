package com.mexc.api.vo.order;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/2/6 下午3:31.
 * 委托信息
 */
public class EntrustInfo {
    private String orderId;

    private String bargainId;

    private String bargainName;

    private Integer dealStatus;

    private Date dealTime;

    private String dealPrice;

    private String dealCount;
    /**
     * 成交率
     */
    private String turnoverRate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public String getBargainName() {
        return bargainName;
    }

    public void setBargainName(String bargainName) {
        this.bargainName = bargainName;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealCount() {
        return dealCount;
    }

    public void setDealCount(String dealCount) {
        this.dealCount = dealCount;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }
}
