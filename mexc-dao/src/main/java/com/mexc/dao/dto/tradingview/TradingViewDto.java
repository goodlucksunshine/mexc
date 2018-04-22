package com.mexc.dao.dto.tradingview;

/**
 * Created by huangxinguang on 2018/1/8 上午10:54.
 * K线请求参数
 */
public class TradingViewDto {

    /**
     * 商品名称或者代码
     */
    private String symbol;
    /**
     * D 1D h
     */
    private String resolution;
    /**
     * 最左侧：unix timestamp (UTC) or leftmost required bar
     */
    private Long from;

    /**
     * 最右侧：unix timestamp (UTC) or rightmost required bar
     */
    private Long to;

    /**
     * 市场ID
     */
    private String marketId;
    /**
     * 币种ID
     */
    private String vcoinId;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
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
