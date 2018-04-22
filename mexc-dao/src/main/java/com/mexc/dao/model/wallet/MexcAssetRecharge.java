package com.mexc.dao.model.wallet;

import java.math.BigDecimal;
import java.util.Date;

public class MexcAssetRecharge {
    private String id;

    /**
     * 交易凭证ID
     */
    private String txHash;

    /**
     * 交易凭证json
     */
    private String txReceipt;

    /**
     * 同步时间
     */
    private Date syncTime;

    /**
     * 用户ID
     */
    private String memberId;

    private String assetAddress;

    private String rechargeAddress;

    private BigDecimal rechargeValue;

    private Date receiptTime;

    private String assetId;

    private String txToken;

    /**
     * 虚拟币id
     */
    private String vcoinId;

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash == null ? null : txHash.trim();
    }

    public String getTxReceipt() {
        return txReceipt;
    }

    public void setTxReceipt(String txReceipt) {
        this.txReceipt = txReceipt == null ? null : txReceipt.trim();
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress == null ? null : assetAddress.trim();
    }

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress == null ? null : rechargeAddress.trim();
    }

    public BigDecimal getRechargeValue() {
        return rechargeValue;
    }

    public void setRechargeValue(BigDecimal rechargeValue) {
        this.rechargeValue = rechargeValue;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getTxToken() {
        return txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken == null ? null : txToken.trim();
    }
}