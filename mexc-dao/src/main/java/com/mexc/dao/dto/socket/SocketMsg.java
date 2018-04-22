package com.mexc.dao.dto.socket;

/**
 * Created by huangxinguang on 2018/1/6 下午1:17.
 * webSocket发送消息
 */
public class SocketMsg {
    /**
     * 1 ：委托订单和交易行情 2：市场和币种行情
     */
    private String type;

    private String marketId;

    private String vcoinId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
