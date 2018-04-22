package com.mexc.dao.dto.order;

/**
 * Created by huangxinguang on 2018/1/5 下午5:47.
 * 客户端发起socket的消息
 */
public class EntrustQuery {

    /**
     * 市场ID
     */
    private String marketId;

    /**
     * 币种ID
     */
    private String vcoinId;

    /**
     * 消息类型 1：委托交易 2：行情
     */
    private String type;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
