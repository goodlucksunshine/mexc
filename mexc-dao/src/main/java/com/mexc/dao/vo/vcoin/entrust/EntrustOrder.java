package com.mexc.dao.vo.vcoin.entrust;

import com.mexc.common.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2017/12/21 下午2:09.
 * 委托买与卖订单
 */
public class EntrustOrder {
    /**
     * 成交价格
     */
    private String tradePrice;
    /**
     * 成交数量
     */
    private String tradeNumberSum;
    /**
     * 成交金额
     */
    private String tradeAmountSum;


    public String getTradePrice() {
        if(StringUtils.isNotEmpty(tradePrice)) {
            return BigDecimalUtil.significand(new BigDecimal(tradePrice),9).toPlainString();
        }
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeNumberSum() {
        if(StringUtils.isNotEmpty(tradeNumberSum)) {
            return BigDecimalUtil.significand(new BigDecimal(tradeNumberSum),9).toPlainString();
        }
        return tradeNumberSum;
    }

    public void setTradeNumberSum(String tradeNumberSum) {
        this.tradeNumberSum = tradeNumberSum;
    }

    public String getTradeAmountSum() {
        if(StringUtils.isNotEmpty(tradeAmountSum)) {
            return BigDecimalUtil.significand(new BigDecimal(tradeAmountSum),9).toPlainString();
        }
        return tradeAmountSum;
    }

    public void setTradeAmountSum(String tradeAmountSum) {
        this.tradeAmountSum = tradeAmountSum;
    }
}
