package com.mexc.dao.dto.asset;

import java.math.BigDecimal;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/14
 * Time: 下午5:09
 */
public class AssetDto {


    private String assetId;

    private String account;

    private String vcoinId;

    private String icon;

    private String vcoinName;

    private String vcoinNameEn;

    private String vcoinNameFull;

    private BigDecimal totalAmount;

    private BigDecimal balanceAmount;

    private BigDecimal btcAmount;

    private BigDecimal frozenAmount;

    private String walletAddress;

    private Integer status;

    private Integer vcoinStatus;

    private String memberId;

    private String iv;

    private String filePath;
    /**
     * 提现费用
     */
    private BigDecimal cashFee;
    /**
     * 最小提现数量
     */
    private BigDecimal cashMin;
    /**
     * 最大提现数量
     */
    private BigDecimal cashMax;

    private String walletId;
    /**
     * 是否可以提现
     */
    private Integer canCash;
    /**
     * 是否可以充值
     */
    private Integer canRecharge;

    /**
     * 充值块数确认
     */
    private Integer sysRechargeBlock;

    /**
     * 提现块数确认
     */
    private Integer sysCashBlock;

    /**
     * 转入冷钱包阀值
     */
    private String thresholdHotToCold;

    public Integer getSysRechargeBlock() {
        return sysRechargeBlock;
    }

    public void setSysRechargeBlock(Integer sysRechargeBlock) {
        this.sysRechargeBlock = sysRechargeBlock;
    }

    public Integer getSysCashBlock() {
        return sysCashBlock;
    }

    public void setSysCashBlock(Integer sysCashBlock) {
        this.sysCashBlock = sysCashBlock;
    }

    public String getThresholdHotToCold() {
        return thresholdHotToCold;
    }

    public void setThresholdHotToCold(String thresholdHotToCold) {
        this.thresholdHotToCold = thresholdHotToCold;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVcoinName() {
        return vcoinName;
    }

    public void setVcoinName(String vcoinName) {
        this.vcoinName = vcoinName;
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn;
    }

    public String getVcoinNameFull() {
        return vcoinNameFull;
    }

    public void setVcoinNameFull(String vcoinNameFull) {
        this.vcoinNameFull = vcoinNameFull;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(BigDecimal btcAmount) {
        this.btcAmount = btcAmount;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVcoinStatus() {
        return vcoinStatus;
    }

    public void setVcoinStatus(Integer vcoinStatus) {
        this.vcoinStatus = vcoinStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BigDecimal getCashFee() {
        return cashFee;
    }

    public void setCashFee(BigDecimal cashFee) {
        this.cashFee = cashFee;
    }

    public BigDecimal getCashMin() {
        return cashMin;
    }

    public void setCashMin(BigDecimal cashMin) {
        this.cashMin = cashMin;
    }

    public BigDecimal getCashMax() {
        return cashMax;
    }

    public void setCashMax(BigDecimal cashMax) {
        this.cashMax = cashMax;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public Integer getCanCash() {
        return canCash;
    }

    public void setCanCash(Integer canCash) {
        this.canCash = canCash;
    }

    public Integer getCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(Integer canRecharge) {
        this.canRecharge = canRecharge;
    }
}
