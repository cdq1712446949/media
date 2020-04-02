package com.cdq.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/31 13:41
 * @description：Cookie工具类
 * @modified By：
 * @version: 1.0.1
 */
public class CookieUtil {

    public static final  String LOGIN_COOKIE = "MEDIA_LOGIN_COOKIE";

    /**
     * 获取cookie中的身份认证cookie
     * @param request
     * @return
     */
    public static String getLoginCookie( HttpServletRequest request){
        if (request.getCookies()!=null){
            for (Cookie cookie: request.getCookies()){
                String tempName = cookie.getName();
                if (tempName.equals(LOGIN_COOKIE)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static  void setLoginCookie(HttpServletResponse response,String value){
        Cookie cookie = new Cookie(LOGIN_COOKIE,value);
//        cookie.setMaxAge();
        response.addCookie(cookie);
    }

}
