package com.mexc.dao.vo.market;

/**
 * Created by huangxinguang on 2018/1/12 下午4:10.
 * 交易对
 */
public class MarketVCoinVo {
    /**
     * id
     */
    private String id;
    /**
     * 市场ID
     */
    private String marketId;
    /**
     * 市场名称
     */
    private String marketName;
    /**
     * 币种ID
     */
    private String vcoinId;
    /**
     * 币种名称
     */
    private String vcoinNameEn;

    /**
     * 交易对状态
     */
    private Integer status;
    /**
     * 是否推荐
     */
    private Integer suggest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSuggest() {
        return suggest;
    }

    public void setSuggest(Integer suggest) {
        this.suggest = suggest;
    }
}
