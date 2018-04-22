package com.mexc.dao.vo.vcoin.trade;

/**
 * Created by huangxinguang on 2018/1/5 下午7:40.
 * 交易24小时统计
 */
public class Trade24HStatisticsVo {
    /**
     * 最高价
     */
    private String maxTradePrice = "0";

    /**
     * 最低价
     */
    private String minTradePrice = "0";

    /**
     * 交易总价
     */
    private String sumTradePrice = "0";

    public String getMaxTradePrice() {
        return maxTradePrice;
    }

    public void setMaxTradePrice(String maxTradePrice) {
        this.maxTradePrice = maxTradePrice;
    }

    public String getMinTradePrice() {
        return minTradePrice;
    }

    public void setMinTradePrice(String minTradePrice) {
        this.minTradePrice = minTradePrice;
    }

    public String getSumTradePrice() {
        return sumTradePrice;
    }

    public void setSumTradePrice(String sumTradePrice) {
        this.sumTradePrice = sumTradePrice;
    }
}
