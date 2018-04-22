package com.mexc.member.dto.request;

import com.laile.esf.common.annotation.validator.ColumnCheck;
import com.laile.esf.common.javaenum.RegexType;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/6
 * Time: 下午4:28
 */
public class UserRegisterRequest {
    /**
     * 注册email
     */
    @ColumnCheck(nullable = false, description = "注册邮箱", regexType = RegexType.EMAIL, maxLength = 30)
    private String regAccount;
    /**
     * 注册密码
     */
    @ColumnCheck(nullable = false, description = "注册密码",maxLength = 64)
    private String hexPassword;
    /**
     * 注册IP
     */
    private String requestIp;

    /**
     * 注册验证码
     */
    private String regCaptchaCode;

    public String getHexPassword() {
        return hexPassword;
    }

    public void setHexPassword(String hexPassword) {
        this.hexPassword = hexPassword;
    }

    public String getRegAccount() {
        return regAccount;
    }

    public void setRegAccount(String regAccount) {
        this.regAccount = regAccount;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRegCaptchaCode() {
        return regCaptchaCode;
    }

    public void setRegCaptchaCode(String regCaptchaCode) {
        this.regCaptchaCode = regCaptchaCode;
    }
}
