package com.mexc.dao.model.vcoin;

import java.math.BigDecimal;
import java.util.Date;

public class MexcTradeDetail {
    private String id;

    private String tradeNo;

    private String transNo;

    private BigDecimal tradePrice;

    private BigDecimal tradeNumber;

    private BigDecimal tradeTotalAmount;

    private BigDecimal tradeRate;

    private BigDecimal tradeFee;
    /**
     * 明细类型 1：交易明细 2：撤单明细
     */
    private Integer type;

    private String memberId;

    private String marketId;

    private String tradeVcoinId;

    private String createBy;

    private Date createTime;

    private String createByName;

    /**
     * 交易单号
     */
    private String tradeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(BigDecimal tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public BigDecimal getTradeTotalAmount() {
        return tradeTotalAmount;
    }

    public void setTradeTotalAmount(BigDecimal tradeTotalAmount) {
        this.tradeTotalAmount = tradeTotalAmount;
    }

    public BigDecimal getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(BigDecimal tradeRate) {
        this.tradeRate = tradeRate;
    }

    public BigDecimal getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId == null ? null : marketId.trim();
    }

    public String getTradeVcoinId() {
        return tradeVcoinId;
    }

    public void setTradeVcoinId(String tradeVcoinId) {
        this.tradeVcoinId = tradeVcoinId == null ? null : tradeVcoinId.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}