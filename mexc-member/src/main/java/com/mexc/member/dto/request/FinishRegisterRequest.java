package com.mexc.member.dto.request;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/7
 * Time: 下午5:45
 */
public class FinishRegisterRequest {

    private String t_k;
    private String from;
    private String s_t;
    private String sign;

    public String getT_k() {
        return t_k;
    }

    public void setT_k(String t_k) {
        this.t_k = t_k;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getS_t() {
        return s_t;
    }

    public void setS_t(String s_t) {
        this.s_t = s_t;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
