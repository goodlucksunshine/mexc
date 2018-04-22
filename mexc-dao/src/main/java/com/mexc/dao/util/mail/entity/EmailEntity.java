package com.mexc.dao.util.mail.entity;

/**
 * Created by huangxinguang on 2018/3/5 下午6:12.
 */
public class EmailEntity {
    private String emailUser;

    private String emailPassword;

    private Integer type;

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
