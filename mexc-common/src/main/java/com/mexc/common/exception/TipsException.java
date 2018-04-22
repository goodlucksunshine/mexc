package com.mexc.common.exception;


/**
 * Created by huangxinguang on 2017/10/18 上午11:08.
 */
public class TipsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 200;

    public TipsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TipsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public TipsException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public TipsException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
