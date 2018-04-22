package com.mexc.web.controller;

import com.alibaba.fastjson.JSON;
import com.mexc.common.base.BaseController;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.member.dto.common.LoginUserDto;
import com.mexc.web.utils.WebUtil;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/14
 * Time: 下午6:04
 */
public abstract class MemberController extends BaseController {

    protected LoginUserDto getSessionUser() {
        String uid = WebUtil.getCookie(getRequest(), "u_id");
        return JSON.parseObject(RedisUtil.get(uid), LoginUserDto.class);
    }

}
