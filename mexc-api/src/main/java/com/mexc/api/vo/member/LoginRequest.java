package com.mexc.api.vo.member;

/**
 * Created by huangxinguang on 2018/2/1 下午4:37.
 */
public class LoginRequest {

    private String mailName;

    private String password;

    private String ip;

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
