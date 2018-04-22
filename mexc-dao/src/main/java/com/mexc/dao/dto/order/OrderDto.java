package com.mexc.dao.dto.order;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/16
 * Time: 下午2:35
 */
public class OrderDto {
    /***交易编号***/
    private String tradeNo;
    /***用户id***/
    private String memberId;
    /***市场id***/
    private String marketId;
    /***市场名称***/
    private String marketName;
    /***虚拟币id***/
    private String tradeVcoinId;
    /***虚拟币名称***/
    private String tradelVcoinNameEn;
    /***委托价***/
    private String tradePrice;
    /***委托均价***/
    private String tradeAvgPrice;
    /***委托数量***/
    private String tradeNumber;
    /**
     * 委托剩余数量
     */
    private String tradeRemainNumber;
    /**
     * 剩余金额
     */
    private String tradeRemainAmount;
    /**
     * 已成交数量
     */
    private String tradedNumber;

    /**
     * 已成交金额
     */
    private String tradeAmount;


    /***委托总额***/
    private String tradeTotalAmount;
    /***trade_type:1-买，2-卖***/
    private String tradeType;
    /***1：未成交 2：已成交 3：部分成交 4：已撤单  5：部撤***/
    private String status;
    /*** 委托时间 ***/
    private String createTime;
    /**
     * 手续费
     */
    private String tradeFee;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getTradeVcoinId() {
        return tradeVcoinId;
    }

    public void setTradeVcoinId(String tradeVcoinId) {
        this.tradeVcoinId = tradeVcoinId;
    }

    public String getTradelVcoinNameEn() {
        return tradelVcoinNameEn;
    }

    public void setTradelVcoinNameEn(String tradelVcoinNameEn) {
        this.tradelVcoinNameEn = tradelVcoinNameEn;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeAvgPrice() {
        return tradeAvgPrice;
    }

    public void setTradeAvgPrice(String tradeAvgPrice) {
        this.tradeAvgPrice = tradeAvgPrice;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getTradeTotalAmount() {
        return tradeTotalAmount;
    }

    public void setTradeTotalAmount(String tradeTotalAmount) {
        this.tradeTotalAmount = tradeTotalAmount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTradeRemainNumber() {
        return tradeRemainNumber;
    }

    public void setTradeRemainNumber(String tradeRemainNumber) {
        this.tradeRemainNumber = tradeRemainNumber;
    }

    public String getTradeRemainAmount() {
        return tradeRemainAmount;
    }

    public void setTradeRemainAmount(String tradeRemainAmount) {
        this.tradeRemainAmount = tradeRemainAmount;
    }

    public String getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(String tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getTradedNumber() {
        if (StringUtils.isEmpty(tradeRemainNumber)) {
            tradeRemainNumber = "0";
        }
        if (StringUtils.isEmpty(tradeNumber)) {
            tradeNumber = "0";
        }
        BigDecimal remainNumber = new BigDecimal(tradeRemainNumber);
        BigDecimal tradeNumberDecimal = new BigDecimal(tradeNumber);
        BigDecimal tradedAmountDecimal = remainNumber.subtract(tradeNumberDecimal);
        return tradedAmountDecimal.toPlainString();
    }

    public String getTradeAmount() {
        if (StringUtils.isEmpty(tradeTotalAmount)) {
            tradeTotalAmount = "0";
        }
        if (StringUtils.isEmpty(tradeRemainAmount)) {
            tradeRemainAmount = "0";
        }
        BigDecimal tradeTotalAmountTemp = new BigDecimal(tradeTotalAmount);
        BigDecimal tradeRemainAmountTemp = new BigDecimal(tradeRemainAmount);
        BigDecimal tradeAmountTemp = tradeTotalAmountTemp.subtract(tradeRemainAmountTemp);
        return tradeAmountTemp.toPlainString();
    }
}
