package com.mexc.dao.dto.order.Match;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2018/3/3 下午3:19.
 * 撮合订单
 */
public class MatchOrder {
    /**
     * 交易订单号
     */
    private String tradeNo;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 委托数量
     */
    private BigDecimal entrustNumber;

    /**
     * 可交易数量
     */
    private BigDecimal tradableNumber;

    /**
     * 交易数量 :默认交易数量为零
     */
    private BigDecimal tradedNumber = new BigDecimal("0");

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 委托时间：yyyyMMddHHmmss
     */
    private String entrustTime;

    /**
     * 市场id
     */
    private String marketId;

    /**
     * 币种id
     */
    private String vcoinId;

    /**
     * 交易类型:1:买，2:卖
     */
    private Integer tradeType;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(BigDecimal entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public BigDecimal getTradableNumber() {
        return tradableNumber;
    }

    public void setTradableNumber(BigDecimal tradableNumber) {
        this.tradableNumber = tradableNumber;
    }

    public BigDecimal getTradedNumber() {
        return tradedNumber;
    }

    public void setTradedNumber(BigDecimal tradedNumber) {
        this.tradedNumber = tradedNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
