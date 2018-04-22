package com.mexc.dao.model.vcoin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MexcTrade implements Serializable {
    private String id;

    private String tradeNo;

    private String memberId;

    private String marketId;

    private String marketName;

    private String tradeVcoinId;

    private String tradelVcoinNameEn;

    private BigDecimal tradePrice;

    private BigDecimal tradeRate;

    /**
     * 手续费
     */
    private BigDecimal tradeFee;

    /**
     * 交易数量
     */
    private BigDecimal tradeNumber;

    /**
     * 交易总额
     */
    private BigDecimal tradeTotalAmount;

    /**
     * 交易方式  trade_type:1-买，2-卖
     */
    private Integer tradeType;

    /**
     * 0：交易中 1：交易成功
     */
    private Integer status;

    private String note;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private String createTime;

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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName == null ? null : marketName.trim();
    }

    public String getTradeVcoinId() {
        return tradeVcoinId;
    }

    public void setTradeVcoinId(String tradeVcoinId) {
        this.tradeVcoinId = tradeVcoinId == null ? null : tradeVcoinId.trim();
    }

    public String getTradelVcoinNameEn() {
        return tradelVcoinNameEn;
    }

    public void setTradelVcoinNameEn(String tradelVcoinNameEn) {
        this.tradelVcoinNameEn = tradelVcoinNameEn == null ? null : tradelVcoinNameEn.trim();
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
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

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdareByName() {
        return updareByName;
    }

    public void setUpdareByName(String updareByName) {
        this.updareByName = updareByName == null ? null : updareByName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}