package com.mexc.web.core.model.response;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/31
 * Time: 下午3:57
 */
public class MemberAssetCashResponse {

    /**
     * 注册结果
     */
    private Integer code;

    /**
     * 注册结果信息
     */
    private String msg;
    /**
     * 是否需要验证谷歌验证码
     */
    private boolean cashCheck;
    /**
     * 实际到账
     */
    private String actualAmount;

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

    public boolean isCashCheck() {
        return cashCheck;
    }

    public void setCashCheck(boolean cashCheck) {
        this.cashCheck = cashCheck;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }
}
