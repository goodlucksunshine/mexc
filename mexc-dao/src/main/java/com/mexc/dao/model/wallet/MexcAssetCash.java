package com.mexc.dao.model.wallet;

import java.math.BigDecimal;
import java.util.Date;

public class MexcAssetCash {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String memberId;

    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 资产地址
     */
    private String assetAddress;

    /**
     * 交易申请时间
     */
    private Date txApplyTime;

    /**
     * 交易申请状态 0:待审核 1:审核通过  -1:审核失败
     */
    private Integer txApplyStatus;

    /**
     * 交易申请邮箱验证码
     */
    private String txTransCode;

    /**
     * 交易申请额
     */
    private BigDecimal txApplyValue;

    /**
     * 交易申请token
     */
    private String txApplyToken;

    /**
     * 谷歌授权码
     */
    private String txGoogleAuth;

    /**
     * 提现地址
     */
    private String txCashAddress;

    /**
     * 交易hash
     */
    private String txHash;

    /**
     * 交易凭据
     */
    private String txReceipt;

    /**
     * 手续费
     */
    private BigDecimal txGaslimit;

    /**
     * 手续费价格
     */
    private BigDecimal txGasprice;

    /**
     * 手续费结算币种金额
     */
    private String txAmount;

    private BigDecimal cashFee;

    /**
     * 提现状态 0:提现待确认 -1:提现失败 -2:提现异常待后台补单 1:提现成功 2:处理中
     */
    private Integer cashStatus;
    /**
     * 实际到账金额
     */
    private BigDecimal actualAmount;

    /**
     * 虚拟币id
     */
    private String vcoinId;
    /**
     * 提现返回信息
     */
    private String errMsg;

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcionId) {
        this.vcoinId = vcionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress == null ? null : assetAddress.trim();
    }

    public Date getTxApplyTime() {
        return txApplyTime;
    }

    public void setTxApplyTime(Date txApplyTime) {
        this.txApplyTime = txApplyTime;
    }

    public Integer getTxApplyStatus() {
        return txApplyStatus;
    }

    public void setTxApplyStatus(Integer txApplyStatus) {
        this.txApplyStatus = txApplyStatus;
    }

    public String getTxTransCode() {
        return txTransCode;
    }

    public void setTxTransCode(String txTransCode) {
        this.txTransCode = txTransCode == null ? null : txTransCode.trim();
    }

    public BigDecimal getTxApplyValue() {
        return txApplyValue;
    }

    public void setTxApplyValue(BigDecimal txApplyValue) {
        this.txApplyValue = txApplyValue;
    }

    public String getTxApplyToken() {
        return txApplyToken;
    }

    public void setTxApplyToken(String txApplyToken) {
        this.txApplyToken = txApplyToken == null ? null : txApplyToken.trim();
    }

    public String getTxGoogleAuth() {
        return txGoogleAuth;
    }

    public void setTxGoogleAuth(String txGoogleAuth) {
        this.txGoogleAuth = txGoogleAuth == null ? null : txGoogleAuth.trim();
    }

    public String getTxCashAddress() {
        return txCashAddress;
    }

    public void setTxCashAddress(String txCashAddress) {
        this.txCashAddress = txCashAddress == null ? null : txCashAddress.trim();
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

    public BigDecimal getTxGaslimit() {
        return txGaslimit;
    }

    public void setTxGaslimit(BigDecimal txGaslimit) {
        this.txGaslimit = txGaslimit;
    }

    public BigDecimal getTxGasprice() {
        return txGasprice;
    }

    public void setTxGasprice(BigDecimal txGasprice) {
        this.txGasprice = txGasprice;
    }

    public String getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(String txAmount) {
        this.txAmount = txAmount == null ? null : txAmount.trim();
    }

    public BigDecimal getCashFee() {
        return cashFee;
    }

    public void setCashFee(BigDecimal cashFee) {
        this.cashFee = cashFee;
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}