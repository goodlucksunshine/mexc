package com.mexc.dao.dto.order;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Created by huangxinguang on 2018/1/4 下午2:03.
 */
public class CancelEntrustTradeDto {
    /**
     * 交易号
     */
    @ColumnCheck(description = "委托单号")
    private String tradeNo;
    /**
     * 账户
     */
    @ColumnCheck(description = "用户账号")
    private String account;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
