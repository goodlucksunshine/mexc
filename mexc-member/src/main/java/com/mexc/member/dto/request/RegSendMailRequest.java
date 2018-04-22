package com.mexc.member.dto.request;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/12
 * Time: 上午11:39
 */
public class RegSendMailRequest {


    /**
     * 重新发送邮件链接账号
     */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
