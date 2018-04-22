package com.mexc.dao.dto.vcoin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangxinguang on 2018/3/10 下午4:51.
 */
public class UpdateEntrustDto {
    /**
     * 委托id
     */
    private String id;
    /**
     * 单号
     */
    private String tradeNo;
    /**
     * 已交易的数量
     */
    private BigDecimal tradedNumber;
    /**
     * 已经交易的金额
     */
    private BigDecimal tradedAmount;

    /**
     * 委托状态
     */
    private Integer status;

    private String updateBy;

    private String updateByName;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTradedNumber() {
        return tradedNumber;
    }

    public void setTradedNumber(BigDecimal tradedNumber) {
        this.tradedNumber = tradedNumber;
    }

    public BigDecimal getTradedAmount() {
        return tradedAmount;
    }

    public void setTradedAmount(BigDecimal tradedAmount) {
        this.tradedAmount = tradedAmount;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
