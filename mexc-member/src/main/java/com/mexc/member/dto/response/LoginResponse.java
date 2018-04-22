package com.mexc.member.dto.response;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/7
 * Time: 下午5:07
 */
public class LoginResponse extends BaseResponse {
    /***
     * 登录状态
     */
    private String status;
    /**
     * 认证等级
     */
    private String authLevel;
    /**
     * 二次认证类型
     */
    private Integer secondAuthType;
    /**
     * 登录账号
     */
    private String account;
    /**
     * 最后一次登录IP
     */
    private String lastLoginIp;
    /**
     * 最后一次登录时间
     */
    private String lastLoginTime;
    /**
     * btc总资产
     */
    private String btcValue;

    /**
     * 美元
     */
    private String usdValue;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(String authLevel) {
        this.authLevel = authLevel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getSecondAuthType() {
        return secondAuthType;
    }

    public void setSecondAuthType(Integer secondAuthType) {
        this.secondAuthType = secondAuthType;
    }

    public String getBtcValue() {
        return btcValue;
    }

    public void setBtcValue(String btcValue) {
        this.btcValue = btcValue;
    }

    public String getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(String usdValue) {
        this.usdValue = usdValue;
    }
}
