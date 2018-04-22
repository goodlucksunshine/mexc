package com.mexc.member.dto.request;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Created by huangxinguang on 2018/3/7 上午10:54.
 */
public class CheckEmailRequest {
    /**
     * 邮箱账号
     */
    @ColumnCheck(nullable = false, description = "注册邮箱", regexType = RegexType.EMAIL, maxLength = 30)
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
