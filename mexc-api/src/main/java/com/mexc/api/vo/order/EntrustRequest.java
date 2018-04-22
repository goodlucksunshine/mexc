package com.mexc.api.vo.order;

/**
 * Created by huangxinguang on 2018/2/6 下午4:03.
 */
public class EntrustRequest {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 交易对ID
     */
    private String bargainId;

    /**
     * 交易类型 1：买 2：卖
     */
    private Integer tradeStatus;

    /**
     * 订单价格
     */
    private String orderPrice;

    /**
     * 订单爽
     */
    private String orderCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
}
