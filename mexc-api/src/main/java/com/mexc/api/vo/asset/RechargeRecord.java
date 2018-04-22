package com.mexc.api.vo.asset;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangxinguang on 2018/2/6 上午11:04.
 * 充值记录
 */
public class RechargeRecord {

    private String userId;

    /**
     * 交易凭证ID
     */
    private String txHash;

    /**
     * 充值地址
     */
    private String rechargeAddress;
    /**
     * 充值金额
     */
    private BigDecimal rechargeValue;

    /**
     * 充值时间
     */
    private Date rechargeTime;

    /**
     * 代币
     */
    private String txToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public BigDecimal getRechargeValue() {
        return rechargeValue;
    }

    public void setRechargeValue(BigDecimal rechargeValue) {
        this.rechargeValue = rechargeValue;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getTxToken() {
        return txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken;
    }
}
