package com.mexc.dao.dto.order.Match;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2018/3/3 下午3:07.
 * 撮合匹配查询
 */
public class MatchOrderQuery {

    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态
     */
    private String status;

    /**
     * 市场ID
     */
    private String marketId;

    /**
     * 币种ID
     */
    private String vcoinId;

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
