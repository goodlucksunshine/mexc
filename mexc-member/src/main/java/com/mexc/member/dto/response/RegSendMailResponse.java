package com.mexc.member.dto.response;

import com.mexc.common.util.SensitiveInfoUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/12
 * Time: 上午11:39
 */
public class RegSendMailResponse extends BaseResponse {
    private String account;

    public String getAccount() {
        if (StringUtils.isNotEmpty(account)) {
            return SensitiveInfoUtils.email(account);
        } else {
            return account;
        }
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
