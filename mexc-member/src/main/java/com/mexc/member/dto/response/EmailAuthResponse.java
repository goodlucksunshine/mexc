package com.mexc.member.dto.response;

/**
 * Created by huangxinguang on 2018/3/7 上午10:39.
 */
public class EmailAuthResponse extends BaseResponse {
    /**
     * 用户状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
