package com.mexc.dao.model.wallet;

import java.math.BigDecimal;
import java.util.Date;

public class MexcColdTrans {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 转账凭证ID
     */
    private String txHash;

    /**
     * 转账地址
     */
    private String fromAddress;

    /**
     * 接收地址
     */
    private String toAddress;

    /**
     * gas使用
     */
    private BigDecimal gasUsed;

    /**
     * gas价格
     */
    private BigDecimal gasPrice;

    private Date createTim;

    private String memberId;

    private String txReceipt;

    /**
     * 0:初始创建 1:交易成功 -1交易失败
     */
    private Integer status;

    /**
     * 交易数量
     */
    private BigDecimal txAmount;

    private String txToken;

    private String assetId;

    /**
     * 明细类型 1:入账 -1:出账
     */
    private Integer txType;

    /**
     * 交易数量
     */
    private BigDecimal txFee;

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

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress == null ? null : toAddress.trim();
    }

    public BigDecimal getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(BigDecimal gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigDecimal getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigDecimal gasPrice) {
        this.gasPrice = gasPrice;
    }

    public Date getCreateTim() {
        return createTim;
    }

    public void setCreateTim(Date createTim) {
        this.createTim = createTim;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getTxReceipt() {
        return txReceipt;
    }

    public void setTxReceipt(String txReceipt) {
        this.txReceipt = txReceipt == null ? null : txReceipt.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public String getTxToken() {
        return txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken == null ? null : txToken.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public Integer getTxType() {
        return txType;
    }

    public void setTxType(Integer txType) {
        this.txType = txType;
    }

    public BigDecimal getTxFee() {
        return txFee;
    }

    public void setTxFee(BigDecimal txFee) {
        this.txFee = txFee;
    }
}