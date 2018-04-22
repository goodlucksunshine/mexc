package com.mexc.member.dto.request;

/**
 * Created by huangxinguang on 2017/12/13 下午5:58.
 */
public class GoogleAuthRequest {
    /**
     * 账户
     */
    private String account;

    /**
     * 生成的编码
     */
    private Long validationCode;
    /**
     * 用户登陆密码
     */
    private String password;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(Long validationCode) {
        this.validationCode = validationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
