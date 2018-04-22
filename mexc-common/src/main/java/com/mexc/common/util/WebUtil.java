package com.mexc.common.util;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/12
 * Time: 下午5:29
 */
public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public WebUtil() {
    }

    public static Boolean isWeChatRequest(HttpServletRequest request) {
        String agent = null;
        Boolean flag = false;
        Enumeration headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            if ("User-Agent".equalsIgnoreCase(name)) {
                agent = request.getHeader(name).toLowerCase();
                flag = agent.contains("micromessenger");
                break;
            }
        }

        return flag;
    }

    public static Boolean isAjax(HttpServletRequest request) {
        Boolean flag = false;
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            flag = true;
        }

        return flag;
    }

    public static void writeJson(HttpServletResponse response, ModelMap model) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(model));
            out.flush();
        } catch (IOException var7) {
            logger.error("response输出Json异常:" + var7);
        } finally {
            out.close();
        }

    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);

        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge.intValue());
            }

            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }

            if (secure != null) {
                cookie.setSecure(secure.booleanValue());
            }

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException var9) {
            logger.error("URL encoder异常:", var9);
        }

    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
        addCookie(request, response, name, value, maxAge, "/", "", (Boolean) null);
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        addCookie(request, response, name, value, (Integer) null, "/", "", (Boolean) null);
    }


    public static String addLoginToken(HttpServletRequest request, HttpServletResponse response, String user) {
        String ip = getIpAddr(request);
        String tokenSource = ip + user;
        String token = MD5Util.MD5(tokenSource);
        addCookie(request, response, "u_id", token);
        return token;
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request);
        Assert.hasText(name);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                Cookie[] var3 = cookies;
                int var4 = cookies.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    Cookie cookie = var3[var5];
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException var7) {
                logger.error("URL encoder异常:", var7);
            }
        }

        return null;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);

        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, (String) null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException var6) {
            logger.error("URL encoder异常:", var6);
        }

    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        removeCookie(request, response, name, "/", "");
    }

    public static String getParameter(String queryString, String encoding, String name) {
        String[] parameterValues = (String[]) ((String[]) getParameterMap(queryString, encoding).get(name));
        return parameterValues != null && parameterValues.length > 0 ? parameterValues[0] : null;
    }

    public static String[] getParameterValues(String queryString, String encoding, String name) {
        return (String[]) ((String[]) getParameterMap(queryString, encoding).get(name));
    }

    public static Map<String, String[]> getParameterMap(String queryString, String encoding) {
        Map<String, String[]> parameterMap = new HashMap();
        Charset charset = Charset.forName(encoding);
        if (StringUtils.isNotEmpty(queryString)) {
            byte[] bytes = queryString.getBytes(charset);
            if (bytes != null && bytes.length > 0) {
                int ix = 0;
                int ox = 0;
                String key = null;
                String value = null;

                while (ix < bytes.length) {
                    byte c = bytes[ix++];
                    switch ((char) c) {
                        case '%':
                            bytes[ox++] = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
                            break;
                        case '&':
                            value = new String(bytes, 0, ox, charset);
                            if (key != null) {
                                putMapEntry(parameterMap, key, value);
                                key = null;
                            }

                            ox = 0;
                            break;
                        case '+':
                            bytes[ox++] = 32;
                            break;
                        case '=':
                            if (key == null) {
                                key = new String(bytes, 0, ox, charset);
                                ox = 0;
                            } else {
                                bytes[ox++] = c;
                            }
                            break;
                        default:
                            bytes[ox++] = c;
                    }
                }

                if (key != null) {
                    value = new String(bytes, 0, ox, charset);
                    putMapEntry(parameterMap, key, value);
                }
            }
        }

        return parameterMap;
    }

    private static void putMapEntry(Map<String, String[]> map, String name, String value) {
        String[] newValues = null;
        String[] oldValues = (String[]) ((String[]) map.get(name));
        if (oldValues == null) {
            newValues = new String[]{value};
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }

        map.put(name, newValues);
    }

    private static byte convertHexDigit(byte b) {
        if (b >= 48 && b <= 57) {
            return (byte) (b - 48);
        } else if (b >= 97 && b <= 102) {
            return (byte) (b - 97 + 10);
        } else if (b >= 65 && b <= 70) {
            return (byte) (b - 65 + 10);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }

        return ip;
    }

    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public static HttpSession getSession(HttpServletRequest request, Boolean flag) {
        return request.getSession(flag.booleanValue());
    }

    public static String getHttpBody(HttpServletRequest request) {
        String data = "";

        try {
            InputStream in = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            for (String temp = ""; (temp = br.readLine()) != null; data = data + temp) {
                ;
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        if (data.indexOf("BODY_DATA") != -1) {
            try {
                data = URLDecoder.decode(data.substring("BODY_DATA=".length()), "utf-8");
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
            }
        }

        return data;
    }
}
