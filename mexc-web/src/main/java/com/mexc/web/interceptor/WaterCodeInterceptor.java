package com.mexc.web.interceptor;

import com.laile.esf.common.util.TrackUtil;
import com.mexc.web.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/18
 * Time: 上午11:33
 */
public class WaterCodeInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WaterCodeInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jessionId = WebUtil.getCookie(request, "JSESSIONID");
        StringBuffer logBf = new StringBuffer();
        String requestUrl = request.getServletPath().trim();
        HttpSession session = WebUtil.getSession(request, false);
        String sessionId = session == null ? null : session.getId();
        logBf.append("==========Access Url:").append(requestUrl).append(",JsessionId:").append(jessionId).append(",sessionId:").append(sessionId).append(",channel:").append("MEXC");
        Enumeration headers = request.getHeaderNames();
        logger.info(logBf.toString());
        String requestNo = TrackUtil.generateChannelFlowNo("MEX");
        request.setAttribute("requestNo", requestNo);
        request.setAttribute("sessionId", jessionId);
        MDC.put("requestNo", requestNo);
        return true;
    }
}
