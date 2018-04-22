package com.mexc.dao.javaenum;

/**
 * Created by huangxinguang on 2018/1/4 下午8:34.
 * 交易订单状态
 */
public enum  TradeOrderEnum {

    UNTRADE("未交易",0),TRADE("已交易",1);

    private String desc;

    private Integer status;

    TradeOrderEnum(String desc,Integer status) {
        this.status = status;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
