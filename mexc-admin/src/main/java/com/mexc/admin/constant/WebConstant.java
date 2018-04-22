package com.mexc.admin.constant;

import com.laile.esf.common.config.PropertyPlaceholderConfigurer;

/**
 * Created by huangxinguang on 2017/11/20 下午4:07.
 */
public class WebConstant {
    /**
     * 验证码
     */
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";

    /**
     * ession用的用户
     */
    public static final String SESSION_USER = "sessionUser";
    /**
     * 登陆错误次数限制
     */
    public static Integer LOGIN_LIMIT_COUNT = Integer.valueOf(PropertyPlaceholderConfigurer.getProperty("loginLimitCount"));


    public static String VCOIN_LIST = PropertyPlaceholderConfigurer.getProperty("code_list");

}
