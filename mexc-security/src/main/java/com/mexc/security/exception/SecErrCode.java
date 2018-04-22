package com.mexc.security.exception;

import com.laile.esf.common.exception.ResultCode;

/**
 * Created by sunshine on 16/7/25.
 */
public enum SecErrCode implements ResultCode {
    /**
     * 账号/密码错误
     */
    SEC001("SEC001"),
    /**
     * 账号被锁定
     */
    SEC002("SEC002");


    private String code;

    SecErrCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return null;
    }
}
