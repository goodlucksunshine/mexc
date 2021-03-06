package com.mexc.api.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("success", true);
    }

    public static R error() {
        return error(false, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(false, msg);
    }

    public static R error(Boolean code, String msg) {
        R r = new R();
        r.put("success", code);
        r.put("msg", msg);
        return r;
    }


    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }


    public static R ok(Object value) {
        R r = new R();
        r.put("success", true);
        r.put("msg", value);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
