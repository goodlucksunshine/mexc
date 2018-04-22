package com.mexc.dao.vo.vcoin.trade;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/5 下午7:36.
 * 最新交易
 */
public class LatestTradeVo {
    /**
     * 交易价格
     */
    private String tradePrice = "0.0";
    /**
     * 交易数量
     */
    private String tradeNumber = "0.0";
    /**
     * 交易金额
     */
    private String tradeAmount = "0.0";
    /**
     * 交易时间
     */
    private Date tradeTime;

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }
}
