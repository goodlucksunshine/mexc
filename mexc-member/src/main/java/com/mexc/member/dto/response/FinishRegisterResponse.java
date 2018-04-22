package com.mexc.member.dto.response;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/7
 * Time: 下午5:45
 */
public class FinishRegisterResponse extends BaseResponse {
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
