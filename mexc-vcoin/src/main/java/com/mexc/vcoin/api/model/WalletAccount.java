package com.mexc.vcoin.api.model;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/25
 * Time: 下午2:54
 */
public class WalletAccount {

    /**
     * 虚拟币主币
     */
    private String vcoinCode;

    /**
     * 秘钥
     */
    private String secret;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 账号短语
     */
    private String phrase;
    /**
     * 钱包地址
     */
    private String address;
    /**
     * 钱包ID
     */
    private String walletId;
    /**
     * 以太坊钱包文件地址
     */
    private String filePath;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVcoinCode() {
        return vcoinCode;
    }

    public void setVcoinCode(String vcoinCode) {
        this.vcoinCode = vcoinCode;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
