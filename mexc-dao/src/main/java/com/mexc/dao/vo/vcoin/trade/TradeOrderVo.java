package com.mexc.dao.vo.vcoin.trade;

import com.mexc.common.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangxinguang on 2017/12/21 下午3:24.
 * 最近交易
 */
public class TradeOrderVo {
    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 交易数量
     */
    private String tradePrice;

    /**
     * 交易数量
     */
    private String tradeNumber;

    /**
     * 交易类型 1：买 2：卖
     */
    private String tradeType;


    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeNumber() {
        if(StringUtils.isNotEmpty(tradeNumber)) {
            return BigDecimalUtil.significand(new BigDecimal(tradeNumber),9).toPlainString();
        }
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getTradePrice() {
        if(StringUtils.isNotEmpty(tradePrice)) {
            return BigDecimalUtil.significand(new BigDecimal(tradePrice),9).toPlainString();
        }
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
