package com.mexc.api.vo.member;

/**
 * Created by huangxinguang on 2018/2/5 下午5:07.
 */
public class ResetPwdRequest {
    /**
     * 邮箱
     */
    private String emailName;

    /**
     * 新密码
     */
    private String nPassword;

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getnPassword() {
        return nPassword;
    }

    public void setnPassword(String nPassword) {
        this.nPassword = nPassword;
    }
}
