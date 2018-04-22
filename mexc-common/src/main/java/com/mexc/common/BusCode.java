package com.mexc.common;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/6
 * Time: 下午4:36
 */
public enum BusCode {

    MEXC_00000(0, "操作成功"),
    MEXC_00001(1, "用户已存在"),
    MEXC_00002(2, "用户名/密码不正确"),
    MEXC_00003(3, "链接已失效,请登录后重新发送链接"),
    MEXC_00004(4, "您已完成注册,请登录使用"),
    MEXC_00005(5, "用户不存在,请先注册"),
    MEXC_00006(6, "重复登录"),
    MEXC_00007(7, "密码不正确"),
    MEXC_00008(8, "验证码不正确"),
    MEXC_00009(9, "原密码不正确"),
    MEXC_00010(10, "提现数量过小"),
    MEXC_00011(11, "提现数量过大"),
    MEXC_00012(12, "余额不足"),
    MEXC_00013(13, "用户未完成注册"),
    MEXC_00014(14, "你当日的提现额度用完了"),
    MEXC_99999(15, "操作失败,请重新尝试");

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

    BusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}


