package com.mexc.api.vo.order;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/2/6 下午2:57.
 * 成交历史
 */
public class DealHisInfo {
    /**
     * 交易对ID
     */
    private String bargainId;
    /**
     * 交易对名称
     */
    private String bargainName;
    /**
     * 交易类型 1：买入 2：卖出
     */
    private Integer dealStatus;
    /**
     * 交易时间
     */
    private Date dealTime;
    /**
     * 价格
     */
    private String dealPrice;
    /**
     * 数量
     */
    private String dealCount;
    /**
     * 手续费
     */
    private String dealFee;

    /**
     * 成交金额
     */
    private String turnoverMoney;

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

    public String getDealFee() {
        return dealFee;
    }

    public void setDealFee(String dealFee) {
        this.dealFee = dealFee;
    }

    public String getTurnoverMoney() {
        return turnoverMoney;
    }

    public void setTurnoverMoney(String turnoverMoney) {
        this.turnoverMoney = turnoverMoney;
    }
}
