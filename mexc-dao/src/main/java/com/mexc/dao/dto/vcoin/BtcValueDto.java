package com.mexc.dao.dto.vcoin;

/**
 * Created by huangxinguang on 2018/1/19 下午5:14.
 * btc估值
 */
public class BtcValueDto {
    /**
     * 币种名称
     */
    private String vcoinNameEn;

    /**
     * 当前币种价格
     */
    private String priceCurrent;

    /**
     * btc估值价格
     */
    private String priceBtc;

    /**
     * 美元估值
     */
    private String priceUsb;

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn;
    }

    public String getPriceCurrent() {
        return priceCurrent;
    }

    public void setPriceCurrent(String priceCurrent) {
        this.priceCurrent = priceCurrent;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String getPriceUsb() {
        return priceUsb;
    }

    public void setPriceUsb(String priceUsb) {
        this.priceUsb = priceUsb;
    }
}
