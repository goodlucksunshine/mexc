package com.mexc.dao.vo.vcoin.trade;

/**
 * Created by huangxinguang on 2018/1/12 下午5:45.
 * 交易对行情
 */
public class TradeGroupInfoVo {
    /**
     * 市场ID
     */
    private String marketId;

    /**
     * 市场ID
     */
    private String marketName;
    /**
     * 币种ID
     */
    private String vcoinId;

    /**
     * 币种因英文名称
     */
    private String vcoinNameEn;

    /**
     * 币种图标
     */
    private String vcoinIcon;

    /**
     * 最新价
     */
    private String latestTradePrice = "0";

    /**
     * 涨跌
     */
    private String upOrDown = "0";

    /**
     * 涨跌值
     */
    private String upOrDownValue="0";

    /**
     * 涨跌率
     */
    private String upOrDownRate = "0%";

    /**
     * 涨跌率值
     */
    private String upOrDownRateValue="0";

    /**
     * 24小时最高价
     */
    private String maxTradePrice = "0";

    /**
     * 24小时最低价
     */
    private String minTradePrice = "0";

    /**
     * 24小时成交额
     */
    private String sumTradePrice = "0";

    /**
     * 是否收藏
     */
    private Integer collect = 0;

    /**
     * 是否推荐
     */
    private Integer suggest = 0;

    /**
     * 最新价的btc估值
     */
    private String btcValue = "0";

    /**
     * 最新价的美元估值
     */
    private String usdValue = "0";

    public String getVcoinIcon() {
        return vcoinIcon;
    }

    public void setVcoinIcon(String vcoinIcon) {
        this.vcoinIcon = vcoinIcon;
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

    public String getLatestTradePrice() {
        return latestTradePrice;
    }

    public void setLatestTradePrice(String latestTradePrice) {
        this.latestTradePrice = latestTradePrice;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    public String getUpOrDownRate() {
        return upOrDownRate;
    }

    public void setUpOrDownRate(String upOrDownRate) {
        this.upOrDownRate = upOrDownRate;
    }

    public String getMaxTradePrice() {
        return maxTradePrice;
    }

    public void setMaxTradePrice(String maxTradePrice) {
        this.maxTradePrice = maxTradePrice;
    }

    public String getMinTradePrice() {
        return minTradePrice;
    }

    public void setMinTradePrice(String minTradePrice) {
        this.minTradePrice = minTradePrice;
    }

    public String getSumTradePrice() {
        return sumTradePrice;
    }

    public void setSumTradePrice(String sumTradePrice) {
        this.sumTradePrice = sumTradePrice;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getSuggest() {
        return suggest;
    }

    public void setSuggest(Integer suggest) {
        this.suggest = suggest;
    }

    public String getBtcValue() {
        return btcValue;
    }

    public void setBtcValue(String btcValue) {
        this.btcValue = btcValue;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn;
    }

    public String getUpOrDownValue() {
        return upOrDownValue;
    }

    public void setUpOrDownValue(String upOrDownValue) {
        this.upOrDownValue = upOrDownValue;
    }

    public String getUpOrDownRateValue() {
        return upOrDownRateValue;
    }

    public void setUpOrDownRateValue(String upOrDownRateValue) {
        this.upOrDownRateValue = upOrDownRateValue;
    }

    public String getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(String usdValue) {
        this.usdValue = usdValue;
    }
}
