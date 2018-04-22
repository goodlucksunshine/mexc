package com.mexc.web.core.model.request;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/30
 * Time: 下午6:40
 */
public class UserWalletToClodRequest {
    /**
     * 用户钱包ID 多钱包时每个钱包地址都会有一个钱包ID
     */
    private String walletId;
    /**
     * 用户钱包地址密文
     */
    private String userWalletAddressSecret;
    /**
     * 用户钱包文件地址
     */
    private String userWalletFile;
    /**
     * 用户钱包密码密文
     */
    private String userPwdSecret;
    /**
     * 向量 密码解密使用
     */
    private String iv;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 是否主币
     */
    private Integer mainCoin;
    /**
     * 合约地址 非主币时使用
     */
    private String contractAddress;
    /**
     * 用户ID
     */
    private String memberId;
    /**
     * 转账数量
     */
    private BigDecimal value;
    /**
     * 主币token
     */
    private String mainToken;
    /**
     * 代币token
     */
    private String token;
    /**
     * 资产ID
     */
    private String assetId;
    /**
     * 交易日期
     */
    private Date transDate;

    /**
     * 小数位
     */
    private Integer scale;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserWalletAddressSecret() {
        return userWalletAddressSecret;
    }

    public void setUserWalletAddressSecret(String userWalletAddressSecret) {
        this.userWalletAddressSecret = userWalletAddressSecret;
    }

    public String getUserWalletFile() {
        return userWalletFile;
    }

    public void setUserWalletFile(String userWalletFile) {
        this.userWalletFile = userWalletFile;
    }

    public String getUserPwdSecret() {
        return userPwdSecret;
    }

    public void setUserPwdSecret(String userPwdSecret) {
        this.userPwdSecret = userPwdSecret;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMainToken() {
        return mainToken;
    }

    public void setMainToken(String mainToken) {
        this.mainToken = mainToken;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
