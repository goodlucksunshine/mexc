package com.mexc.api.vo.member;

import com.mexc.api.vo.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/2/1 下午4:36.
 */
public class LoginResponse extends BaseResponse {
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * btc估值
     */
    private String btcProperties;
    /**
     * 美元估值
     */
    private String usdProperties;
    /**
     * 其他币种资产
     */
    private List<Map<String,Object>> otherProperties;
    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 0 未认证 1：已认证
     */
    private Integer isGoogleAuthed;

    private Integer isIdentityAuthed;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBtcProperties() {
        return btcProperties;
    }

    public void setBtcProperties(String btcProperties) {
        this.btcProperties = btcProperties;
    }

    public String getUsdProperties() {
        return usdProperties;
    }

    public void setUsdProperties(String usdProperties) {
        this.usdProperties = usdProperties;
    }

    public List<Map<String, Object>> getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(List<Map<String, Object>> otherProperties) {
        this.otherProperties = otherProperties;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Integer getIsGoogleAuthed() {
        return isGoogleAuthed;
    }

    public void setIsGoogleAuthed(Integer isGoogleAuthed) {
        this.isGoogleAuthed = isGoogleAuthed;
    }

    public Integer getIsIdentityAuthed() {
        return isIdentityAuthed;
    }

    public void setIsIdentityAuthed(Integer isIdentityAuthed) {
        this.isIdentityAuthed = isIdentityAuthed;
    }
}
