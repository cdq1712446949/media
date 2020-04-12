package com.cdq.filter;

import com.cdq.util.HttpResponse404;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/10 10:37
 * @description：检查url是否合法
 * @modified By：
 * @version: 1.0.1
 */
public class UrlFilter extends ZuulFilter {

    //合法的url根路径
    public static String[] URLS={
       "media","video","article","other","user"
    } ;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取请求地址
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURL());
        String[] tempUrls = request.getRequestURL().toString().split("\\/");
        for (String url:URLS){
            if (tempUrls[0].equals("url")){
                return null;
            }
        }
        return new HttpResponse404();
    }
}
