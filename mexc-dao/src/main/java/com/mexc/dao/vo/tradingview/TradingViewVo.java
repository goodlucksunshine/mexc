package com.mexc.dao.vo.tradingview;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2018/1/9 下午2:22.
 */
public class TradingViewVo {

    /**
     * 状态码
     */
    private String s;

   /* *//**
     * 错误码
     *//*
    private String errmsg;*/

    /**
     * 当前时间
     */
    private List<Long> t;

    /**
     * 开盘价 (可选)
     */
    private List<Double> o;
    /**
     * 最高价（可选）
     */
    private List<Double> h;
    /**
     * 最低价(可选)
     */
    private List<Double> l;

    /**
     * 收盘价
     */
    private List<Double> c;

    /**
     * 成交量 (可选)
     */
    private List<Double> v;

/*    *//**
     * 下一个K线柱的时间 如果在请求期间无数据 (状态码为no_data) (可选)
     *//*
    private Long nextTime;*/

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

/*    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }*/

    public List<Long> getT() {
        return t;
    }

    public void setT(List<Long> t) {
        this.t = t;
    }

    public List<Double> getO() {
        return o;
    }

    public void setO(List<Double> o) {
        this.o = o;
    }

    public List<Double> getH() {
        return h;
    }

    public void setH(List<Double> h) {
        this.h = h;
    }

    public List<Double> getL() {
        return l;
    }

    public void setL(List<Double> l) {
        this.l = l;
    }

    public List<Double> getC() {
        return c;
    }

    public void setC(List<Double> c) {
        this.c = c;
    }

    public List<Double> getV() {
        return v;
    }

    public void setV(List<Double> v) {
        this.v = v;
    }

/*    public Long getNextTime() {
        return nextTime;
    }

    public void setNextTime(Long nextTime) {
        this.nextTime = nextTime;
    }*/
}
