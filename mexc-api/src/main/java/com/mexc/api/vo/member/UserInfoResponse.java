package com.mexc.api.vo.member;

import com.mexc.api.vo.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/2/5 下午6:18.
 */
public class UserInfoResponse extends BaseResponse {

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * btc资产
     */
    private String btcProperties;
    /**
     * 美元资产
     */
    private String usdPropertis;

    /**
     * 其他币种资产
     */
    private List<Map<String,Object>> otherCoinProps;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsdPropertis() {
        return usdPropertis;
    }

    public void setUsdPropertis(String usdPropertis) {
        this.usdPropertis = usdPropertis;
    }

    public List<Map<String, Object>> getOtherCoinProps() {
        return otherCoinProps;
    }

    public void setOtherCoinProps(List<Map<String, Object>> otherCoinProps) {
        this.otherCoinProps = otherCoinProps;
    }
}
