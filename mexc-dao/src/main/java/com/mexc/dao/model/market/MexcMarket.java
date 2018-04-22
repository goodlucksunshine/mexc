package com.mexc.dao.model.market;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MexcMarket implements Serializable {
    private String id;

    private String vcoinId;

    private String marketName;

    /**
     * 交易最大限额
     */
    private BigDecimal tradeMaxAmount;

    /**
     * 交易最小限额
     */
    private BigDecimal tradeMinAmount;

    /**
     * 排序,按数字从低到高0、1、2、3......
     */
    private Integer sort;

    /**
     * 状态：1-正常，2-关闭
     */
    private Integer status;

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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName == null ? null : marketName.trim();
    }

    public BigDecimal getTradeMaxAmount() {
        return tradeMaxAmount;
    }

    public void setTradeMaxAmount(BigDecimal tradeMaxAmount) {
        this.tradeMaxAmount = tradeMaxAmount;
    }

    public BigDecimal getTradeMinAmount() {
        return tradeMinAmount;
    }

    public void setTradeMinAmount(BigDecimal tradeMinAmount) {
        this.tradeMinAmount = tradeMinAmount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }


}