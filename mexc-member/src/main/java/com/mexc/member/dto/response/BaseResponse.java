package com.mexc.member.dto.response;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/7
 * Time: 下午5:16
 */
public class BaseResponse {

    /**
     * 注册结果
     */
    private Integer code;

    /**
     * 注册结果信息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
