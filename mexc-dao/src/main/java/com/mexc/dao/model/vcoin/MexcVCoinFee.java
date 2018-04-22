package com.mexc.dao.model.vcoin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MexcVCoinFee implements Serializable {
    private String id;

    private String vcoinId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId == null ? null : vcoinId.trim();
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
        this.note = note == null ? null : note.trim();
    }
}