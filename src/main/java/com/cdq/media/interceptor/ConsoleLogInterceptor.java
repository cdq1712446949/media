package com.cdq.media.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 15:53
 * @description：控制台输出controller被访问方法的参数
 * @modified By：
 * @version: 1.0.1
 */
public class ConsoleLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("\n");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("|  class and method:" + handler.toString());
        System.out.print("|  args:");
        Enumeration<String> argsList = request.getParameterNames();
        while (argsList.hasMoreElements()) {
            String s = (String) argsList.nextElement();
            System.out.print(s + ":" + request.getParameter(s) + ",");
        }
        System.out.print("\n");
        System.out.println("----------------------------------------------------------------------------");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
