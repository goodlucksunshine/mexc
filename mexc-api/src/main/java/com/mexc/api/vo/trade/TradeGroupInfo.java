package com.mexc.api.vo.trade;

/**
 * Created by huangxinguang on 2018/2/5 下午7:33.
 * 交易对信息
 */
public class TradeGroupInfo {
    /**
     * 交易对ID
     */
    private String bargainId;

    /**
     * 1：收藏 0：不收藏
     */
    private Integer isCollected;
    /**
     * 主币
     */
    private String mainBargain;
    /**
     * 交易币
     */
    private String exchangeBargain;
    /**
     * 24小时成交量
     */
    private String tradingVolume;
    /**
     * 最新交易价
     */
    private String exchangePrice;
    /**
     * 美元价
     */
    private String currencyPrice;
    /**
     * 是否是涨还是跌
     */
    private Integer upOrDownTag;
    /**
     * 涨跌幅
     */
    private String upOrDownRange;

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }

    public String getMainBargain() {
        return mainBargain;
    }

    public void setMainBargain(String mainBargain) {
        this.mainBargain = mainBargain;
    }

    public String getExchangeBargain() {
        return exchangeBargain;
    }

    public void setExchangeBargain(String exchangeBargain) {
        this.exchangeBargain = exchangeBargain;
    }

    public String getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(String tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public String getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(String exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public Integer getUpOrDownTag() {
        return upOrDownTag;
    }

    public void setUpOrDownTag(Integer upOrDownTag) {
        this.upOrDownTag = upOrDownTag;
    }

    public String getUpOrDownRange() {
        return upOrDownRange;
    }

    public void setUpOrDownRange(String upOrDownRange) {
        this.upOrDownRange = upOrDownRange;
    }
}
