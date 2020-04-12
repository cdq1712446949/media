package com.cdq.filter;

import com.cdq.util.ConstansUtil;
import com.cdq.util.MyHttpResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/2 13:30
 * @description：检查用户身份认证信息
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class LoginFilter extends ZuulFilter {

    public static String MEDIA = "media";
    public static String USER = "user";
    public static String VIDEO = "video";
    public static String ARTICLE = "article";

    private static String USER_URL = "http://SERVER-USER/";

    @Autowired
    private RestTemplate restTemplate;

    //表示不用拦截的url
    private static String[] INVOKE_USER_URL = new String[]{
            "login"
    };
    private static String[] INVOKE_ARTICLE_URL = new String[]{
            "alat"
    };
    private static String[] INVOKE_VIDEO_URL = new String[]{

    };

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示当前拦截器是否启用
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //获取请求地址
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURL());
        String[] tempUrls = request.getRequestURL().toString().split("\\/");
        System.out.println(tempUrls.toString());
        //拦截规则
        if (!checkIsFilter(tempUrls)) {
            return null;
        }
        //获取token转发到鉴权中心
        String token = request.getHeader(ConstansUtil.HEAD_KEY);
        if (token==null||token.equals("")){
            return new MyHttpResponse(HttpStatus.BAD_REQUEST);
        }
        Map resultMap = restTemplate.getForObject(USER_URL + "checkLoginInfo?token=" + token, Map.class);
        if ((boolean)resultMap.get("result")){
            return null;
        }else {
            return resultMap;
        }
    }

    /**
     * 检查请求地址是否需要进行身份认证拦截
     *
     * @param tempUrls
     * @return
     */
    private boolean checkIsFilter(String[] tempUrls) {
        if (tempUrls[3].equals(MEDIA)) {
            return false;
        }
        if (tempUrls[3].equals(USER)) {
            for (String url : INVOKE_USER_URL) {
                if (url.equals(tempUrls[4])) {
                    return false;
                }
            }
        }
        if (tempUrls[3].equals(ARTICLE)) {
            for (String url : INVOKE_ARTICLE_URL) {
                if (url.equals(tempUrls[4])) {
                    return false;
                }
            }
        }
        if (tempUrls[3].equals(VIDEO)) {
            for (String url : INVOKE_VIDEO_URL) {
                if (url.equals(tempUrls[4])) {
                    return false;
                }
            }
        }
        return true;
    }

}
