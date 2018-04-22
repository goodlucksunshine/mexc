package com.mexc.dao.model.wallet;

public class MexcAssetCashVcoin extends MexcAssetCash{
    private String vcoinToken;
    private String vcoinName;
    private String vcoinNameEn;
    private String vcoinNameFull;
    private Integer mainCoin;

    public String getVcoinToken() {
        return vcoinToken;
    }

    public void setVcoinToken(String vcoinToken) {
        this.vcoinToken = vcoinToken;
    }

    public String getVcoinName() {
        return vcoinName;
    }

    public void setVcoinName(String vcoinName) {
        this.vcoinName = vcoinName;
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn;
    }

    public String getVcoinNameFull() {
        return vcoinNameFull;
    }

    public void setVcoinNameFull(String vcoinNameFull) {
        this.vcoinNameFull = vcoinNameFull;
    }

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }
}
