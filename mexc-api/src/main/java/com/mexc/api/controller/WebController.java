package com.mexc.api.controller;

import com.alibaba.fastjson.JSON;
import com.mexc.api.vo.member.LoginResponse;
import com.mexc.common.base.BaseController;
import com.mexc.common.util.WebUtil;
import com.mexc.common.util.jedis.RedisUtil;

/**
 * Created by huangxinguang on 2018/2/6 下午6:46.
 */
public class WebController extends BaseController {

    protected LoginResponse getSessionUser() {
        String uid = WebUtil.getCookie(getRequest(), "u_id");
        return JSON.parseObject(RedisUtil.get(uid), LoginResponse.class);
    }
}
