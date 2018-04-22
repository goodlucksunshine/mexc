package com.mexc.api.interceptor;

import com.mexc.common.util.WebUtil;
import com.mexc.common.util.jedis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/12
 * Time: 下午5:48
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("用户登录拦截器");
        String token = WebUtil.getCookie(request, "u_id");
        logger.info("用户登录拦截器token:{}", token);
        if (StringUtils.isEmpty(token)) {
            logger.info("用户登录拦截器 token为空返回页面");
            response.setStatus(401);
            return false;
        }
        String userInfo = RedisUtil.get(token);
        if (StringUtils.isEmpty(userInfo)) {
            logger.info("用户登录拦截器 缓存中未找到用户相关信息");
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
