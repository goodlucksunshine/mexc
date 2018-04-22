package com.mexc.api.vo.member;


/**
 * Created by huangxinguang on 2018/2/5 下午4:09.
 */
public class RegisterRequest {
    private String emailName;

    private String password;

    private String authcode;

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
