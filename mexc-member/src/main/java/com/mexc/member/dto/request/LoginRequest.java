package com.mexc.member.dto.request;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/7
 * Time: 下午4:49
 */
public class LoginRequest {
    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;
    /**
     * 验证码
     */
    //private String captcha;
    /**
     * 验证码
     */
    private String captchaCode;
    /**
     * ip地址
     */
    private String ip;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }*/

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
