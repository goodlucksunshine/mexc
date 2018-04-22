package com.mexc.member.dto.response;

/**
 * Created by huangxinguang on 2017/12/14 上午10:27.
 */
public class GoogleAuthInfoResponse  extends BaseResponse {
    /**
     * 密匙
     */
    private String secret;
    /**
     * 二维码
     */
    private String qrcodeURL;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getQrcodeURL() {
        return qrcodeURL;
    }

    public void setQrcodeURL(String qrcodeURL) {
        this.qrcodeURL = qrcodeURL;
    }
}
