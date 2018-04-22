package com.mexc.api.vo.asset;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/2/6 上午10:27.
 * 提现记录
 */
public class CashRecord {
    private String recordId;

    private String txPrice;

    private Date txDate;

    private String txAddress;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTxPrice() {
        return txPrice;
    }

    public void setTxPrice(String txPrice) {
        this.txPrice = txPrice;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public String getTxAddress() {
        return txAddress;
    }

    public void setTxAddress(String txAddress) {
        this.txAddress = txAddress;
    }
}
