package com.cdq.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/16 14:54
 * @description：日志拦截器
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class LogFilter extends ZuulFilter {

    /**
     * 返回拦截类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 返回当前拦截器级别，数字越小级别越大，最大是0
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    /**
     * 表示是否启动当前拦截器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 拦截器执行逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return null;
    }
}
