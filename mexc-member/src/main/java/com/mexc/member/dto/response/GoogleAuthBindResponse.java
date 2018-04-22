package com.mexc.member.dto.response;

/**
 * Created by huangxinguang on 2017/12/13 下午5:52.
 */
public class GoogleAuthBindResponse extends BaseResponse {
    /**
     * 密匙
     */
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
