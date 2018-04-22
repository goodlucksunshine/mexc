package com.mexc.dao.vo.asset;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2018/3/9 下午7:31.
 * 提现历史
 */
public class AssetCashHistoryVo {
    /**
     * 币种
     */
    private String vcoinId;
    /**
     * 提现总额
     */
    private BigDecimal cashAmountSum;

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public BigDecimal getCashAmountSum() {
        return cashAmountSum;
    }

    public void setCashAmountSum(BigDecimal cashAmountSum) {
        this.cashAmountSum = cashAmountSum;
    }
}
