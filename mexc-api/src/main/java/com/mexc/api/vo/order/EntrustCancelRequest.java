package com.mexc.api.vo.order;

/**
 * Created by huangxinguang on 2018/2/6 下午4:39.
 * 撤单
 */
public class EntrustCancelRequest  {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
