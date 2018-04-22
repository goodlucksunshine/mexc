package com.mexc.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.StringUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.member.dto.common.LoginUserDto;
import com.mexc.web.constant.WebConstant;
import com.mexc.web.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * socket握手拦截器
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        logger.info("----socket after handshake-----");
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        logger.info("----socket before handshake-----");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            //HttpSession session = servletRequest.getServletRequest().getSession(false);

            String uid = WebUtil.getCookie(servletRequest.getServletRequest(), "u_id");
            if(!StringUtil.isEmpty(uid) && RedisUtil.get(uid) != null) {
                String account = JSON.parseObject(RedisUtil.get(uid), LoginUserDto.class).getAccount();
                attributes.put(WebConstant.DEFAULT_WEBSOCKET_USERNAME, account);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
