package com.mexc.member.dto.response;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/6
 * Time: 下午4:29
 */
public class UserRegisterResponse extends BaseResponse {
    /**
     * 注册账号
     */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
