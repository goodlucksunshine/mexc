package com.mexc.dao.dto.vcoin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangxinguang on 2017/11/27 上午11:25.
 */
public class VCoinDto {

    private String id;

    private String vcoinToken;

    private String vcoinName;

    private String vcoinNameEn;

    private String vcoinNameFull;

    private String icon;

    /**
     * 系统账户
     */
    private String sysAccount;

    /**
     * 交易key
     */
    private String tradeKey;

    /**
     * 状态：接入状态
     */
    private Integer status;

    private Integer sort;

    /**
     * 买单手续费
     */
    private BigDecimal buyRate;

    /**
     * 卖单手续费
     */
    private BigDecimal sellRate;

    /**
     * 挂单手续费
     */
    private BigDecimal putUpRate;

    /**
     * 提现费率
     */
    private BigDecimal cashRate;

    /**
     * 最小提现额
     */
    private BigDecimal cashLimitMin;

    /**
     * 最大体现额
     */
    private BigDecimal cashLimitMax;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;

    private String note;

    private Integer mainCoin;

    private String contractAddress;
    /**
     * 是否可提现
     */
    private Integer canCash;
    /**
     * 是否可充值
     */
    private Integer canRecharge;

    /**
     * 小数位
     */
    private Integer scale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public String getTradeKey() {
        return tradeKey;
    }

    public void setTradeKey(String tradeKey) {
        this.tradeKey = tradeKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public BigDecimal getPutUpRate() {
        return putUpRate;
    }

    public void setPutUpRate(BigDecimal putUpRate) {
        this.putUpRate = putUpRate;
    }

    public BigDecimal getCashRate() {
        return cashRate;
    }

    public void setCashRate(BigDecimal cashRate) {
        this.cashRate = cashRate;
    }

    public BigDecimal getCashLimitMin() {
        return cashLimitMin;
    }

    public void setCashLimitMin(BigDecimal cashLimitMin) {
        this.cashLimitMin = cashLimitMin;
    }

    public BigDecimal getCashLimitMax() {
        return cashLimitMax;
    }

    public void setCashLimitMax(BigDecimal cashLimitMax) {
        this.cashLimitMax = cashLimitMax;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdareByName() {
        return updareByName;
    }

    public void setUpdareByName(String updareByName) {
        this.updareByName = updareByName;
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
        this.createBy = createBy;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVcoinToken() {
        return vcoinToken;
    }

    public void setVcoinToken(String vcoinToken) {
        this.vcoinToken = vcoinToken;
    }

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
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

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
