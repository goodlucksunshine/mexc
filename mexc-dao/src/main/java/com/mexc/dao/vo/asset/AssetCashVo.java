package com.mexc.dao.vo.asset;

import com.mexc.common.util.ThressDescUtil;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/22 下午6:16.
 */
public class AssetCashVo {
    /**
     * 账户
     */
    private String account;

    /**
     * 提现币种
     */
    private String txApplyToken;

    /**
     * 提现时间
     */
    private Date txApplyTime;

    /**
     * 提现地址
     */
    private String txCashAddress;

    /**
     * 提现申请额度
     */
    private String txApplyValue;

    /**
     * 手续费
     */
    private String cashFee;

    /**
     * 提现状态
     */
    private Integer cashStatus;
    /**
     * 提现单号
     */
    private String cashId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTxApplyToken() {
        return txApplyToken;
    }

    public void setTxApplyToken(String txApplyToken) {
        this.txApplyToken = txApplyToken;
    }

    public Date getTxApplyTime() {
        return txApplyTime;
    }

    public void setTxApplyTime(Date txApplyTime) {
        this.txApplyTime = txApplyTime;
    }

    public String getTxCashAddress() {
        if (txCashAddress != null) {
            return ThressDescUtil.decodeAssetAddress(txCashAddress);
        }
        return txCashAddress;
    }

    public void setTxCashAddress(String txCashAddress) {
        this.txCashAddress = txCashAddress;
    }

    public String getTxApplyValue() {
        return txApplyValue;
    }

    public void setTxApplyValue(String txApplyValue) {
        this.txApplyValue = txApplyValue;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }
}
