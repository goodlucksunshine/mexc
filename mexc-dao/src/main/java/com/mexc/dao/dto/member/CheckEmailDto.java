package com.mexc.dao.dto.member;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Created by huangxinguang on 2018/3/7 上午10:27.
 */
public class CheckEmailDto {

    @ColumnCheck(nullable = false, description = "注册邮箱", regexType = RegexType.EMAIL, maxLength = 30)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
