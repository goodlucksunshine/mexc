package com.mexc.dao.dto.wallet;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/3/12
 * Time: 下午8:25
 */
public class BtcWalletInfo {

    /**
     * 热钱包地址
     */
    private String hotAddress;
    /**
     * 冷钱包地址
     */
    private String coldAddress;

    public String getHotAddress() {
        return hotAddress;
    }

    public void setHotAddress(String hotAddress) {
        this.hotAddress = hotAddress;
    }

    public String getColdAddress() {
        return coldAddress;
    }

    public void setColdAddress(String coldAddress) {
        this.coldAddress = coldAddress;
    }
}
