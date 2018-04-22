package com.mexc.api.constant;

/**
 * Created by huangxinguang on 2018/2/6 下午6:50.
 * API结果编码
 */
public enum ResultCode {
    MEXC_API_99999(9999, "操作异常，请重新尝试"),
    MEXC_API_00000(0, "操作成功"),
    MEXC_API_00001(1, "登陆重复"),
    MEXC_API_00002(2, "用户名/密码不正确"),
    MEXC_API_00003(3, "验证码不正确"),
    MEXC_API_00004(4, "该邮箱已注册"),
    MEXC_API_00005(5, "密码重置失败"),
    MEXC_API_00006(6, "用户不存在"),
    MEXC_API_00007(7, "旧密码不正确"),
    MEXC_API_00008(8, "修改密码失败"),
    MEXC_API_00009(9, "交易对不存在"),
    MEXC_API_00010(10, "已收藏过");

    private Integer code;
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

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
