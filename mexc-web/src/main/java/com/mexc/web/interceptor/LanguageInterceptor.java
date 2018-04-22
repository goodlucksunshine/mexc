package com.mexc.web.interceptor;

import com.mexc.web.utils.WebUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by huangxinguang on 2018/2/8 下午4:30.
 *
 */
public class LanguageInterceptor  extends HandlerInterceptorAdapter {
    public static final String DEFAULT_PARAM_NAME = "locale";
    private String paramName = "locale";

    public LanguageInterceptor() {
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return this.paramName;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        //String newLocale = request.getParameter(this.paramName);
        String newLocale = WebUtil.getCookie(request, "lang");
        if (newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
        }

        return true;
    }

}
