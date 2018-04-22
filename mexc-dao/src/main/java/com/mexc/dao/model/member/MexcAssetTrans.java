package com.mexc.dao.model.member;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户资产明细
 */
public class MexcAssetTrans {
    private String id;

    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 交易数量
     */
    private BigDecimal transAmount;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 交易凭证
     */
    private String transReceipt;

    /**
     * 入账类型 -1:提现 1:入账
     */
    private Integer transType;

    private String transNo;

    /**
     * -1:提现 1:冲值 2:委托 3:交易手续费 4:资产返还 5:平台充值 6:活动赠送 7:提现手续费
     */
    private String tradeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransReceipt() {
        return transReceipt;
    }

    public void setTransReceipt(String transReceipt) {
        this.transReceipt = transReceipt == null ? null : transReceipt.trim();
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }
}