package com.mexc.dao.dto.wallet;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/3/12
 * Time: 下午8:23
 */
public class EthWallInfo {
    /**
     * 热钱包地址
     */
    private String hotAddress;
    /**
     * 冷钱包地址
     */
    private String coldAddress;
    /**
     * 热钱包密码
     */
    private String hotAddressPwd;

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

    public String getHotAddressPwd() {
        return hotAddressPwd;
    }

    public void setHotAddressPwd(String hotAddressPwd) {
        this.hotAddressPwd = hotAddressPwd;
    }
}
