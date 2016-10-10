package com.danger.study.tools.http;

import com.danger.study.tools.common.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie操作工具类
 * Created by danger on 2016/6/14.
 */
public class CookieUtils {

    /**
     * 将cookie封装到Map里面
     * @param request 请求
     * @return map
     */
    private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 根据名字获取cookie
     * @param request 请求
     * @param name cookie name
     * @return cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String,Cookie> cookieMap = getCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     * 设置本域名根目录的cookie信息
     * @param response  响应
     * @param name  cookie name
     * @param value cookie 内容
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 0, "", "/");
    }

    /**
     * 设置本域名根目录带有效时间的cookie信息
     * @param response  响应
     * @param name  cookie name
     * @param value cookie 内容
     * @param seconds 有效时间  秒数
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int seconds) {
        setCookie(response, name, value, seconds, "", "/");
    }

    /**
     * 设置本域名根目录带有戏时间和跨域信息的cookie信息
     * @param response  响应
     * @param name  cookie name
     * @param value cookie 内容
     * @param seconds   有效时间    秒数
     * @param domain    跨域信息    "."开头的域名
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int seconds, String domain) {
        setCookie(response, name, value, seconds, domain, "/");
    }

    /**
     * 设置cookie信息
     * @param response 响应
     * @param name cookie name
     * @param value cookie 内容
     * @param seconds 生存时间
     * @param domain 有效域
     * @param path 有效路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int seconds, String domain, String path) {
        if (StringUtils.isEmpty(name)) return;
        Cookie cookie = new Cookie(name, value);
        if (seconds > 0) cookie.setMaxAge(seconds);
        if (! StringUtils.isEmpty(domain)) cookie.setDomain(domain);
        if (! StringUtils.isEmpty(path)) cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie信息
     * @param request   请求
     * @param response  响应
     * @param name      cookie name
     */
    public static void delCookieByName(HttpServletRequest request, HttpServletResponse response, String name, String path) {
        Cookie cookie = getCookieByName(request, name);
        if (null != cookie) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }
}
