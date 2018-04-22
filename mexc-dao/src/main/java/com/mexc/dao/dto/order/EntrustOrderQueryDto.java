package com.mexc.dao.dto.order;

/**
 * Created by huangxinguang on 2017/12/21 下午4:34.
 * 委托订单交易查询
 */
public class EntrustOrderQueryDto {
    /**
     * 市场ID
     */
    private String marketId;
    /**
     * 虚拟币ID
     */
    private String vcoinId;

    /**
     * 交易类型 1：买 2：卖
     */
    private String tradeType;

    private String status;

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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
