package com.mexc.dao.dto.member;

import java.util.Date;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/15
 * Time: 下午4:56
 */
public class LoginInfoDto {

    private String account;

    private Date loginTime;

    private String loginIp;



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
