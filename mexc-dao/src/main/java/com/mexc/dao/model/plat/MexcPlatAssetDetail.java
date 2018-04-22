package com.mexc.dao.model.plat;

import java.math.BigDecimal;
import java.util.Date;

public class MexcPlatAssetDetail {
    private String id;

    /**
     * 资产ID
     */
    private String platId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 手续费
     */
    private BigDecimal tradeFee;

    /**
     * 手续费率
     */
    private BigDecimal tradeRate;

    /**
     * 操作类型:1手续费
     */
    private Integer optType;

    /**
     * 操作时间
     */
    private Date optTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public BigDecimal getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(BigDecimal tradeRate) {
        this.tradeRate = tradeRate;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }
}