package com.mexc.member.dto.request;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Created by huangxinguang on 2018/3/7 上午10:38.
 */
public class EmailAuthRequest {
    /**
     * 邮箱账户
     */
    @ColumnCheck(nullable = false, description = "注册邮箱", regexType = RegexType.EMAIL, maxLength = 30)
    private String account;

    /**
     * 新密码
     */
    @ColumnCheck(nullable = false, description = "注册密码",maxLength = 64)
    private String newPassword;

    /**
     * 邮箱验证码
     */
    @ColumnCheck(nullable = false, description = "验证码",maxLength = 64)
    private String authCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
