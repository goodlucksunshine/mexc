package com.mexc.dao.dto.order;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Created by huangxinguang on 2017/12/25 下午2:25.
 * 委托交易/买卖
 */
public class EntrustTradeDto {
    /**
     * 账户
     */
    private String account;
    /**
     * 市场ID
     */
    private String marketId;

    /**
     * 币种ID
     */
    private String vcoinId;

    /**
     * 交易价格
     */
    @ColumnCheck(description = "交易价格",maxLength = 30)
    private String tradePrice;

    /**
     * 交易数量
     */
    @ColumnCheck(description = "交易数量",maxLength = 30)
    private String tradeNumber;

    /**
     * 交易类型 1：买 2：卖
     */
    @ColumnCheck(description = "交易类型")
    private Integer tradeType;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
