package com.mexc.api.vo;

/**
 * Created by huangxinguang on 2018/2/1 下午5:28.
 */
public class BaseResponse {

    private Integer resultCode;

    private String message;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
