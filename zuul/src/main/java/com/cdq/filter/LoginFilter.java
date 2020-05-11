package com.cdq.filter;

import com.alibaba.fastjson.JSONObject;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
    public static String IMAGE = "image";

    private static String USER_URL = "http://SERVER-USER/";

    @Autowired
    private RestTemplate restTemplate;

    //表示不用拦截的url
    private static String[] INVOKE_USER_URL = new String[]{
            "login", "register"
    };
    private static String[] INVOKE_ARTICLE_URL = new String[]{
            "alat", "aflat", "getArticle", "ugabi", "ugucl",
            "firstarticletypelist", "artideti", "ugall", "ugval"
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
        String token = HttpServletRequestUtil.getString(request, ConstansUtil.TOKEN);
        if (token == null || ConstansUtil.EMPTY.equals(token)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("stateCode", 401);
            resultMap.put("result", false);
            resultMap.put("redirect", "http://media.com/media/login");
            requestContext.setSendZuulResponse(false);
            HttpServletResponse response = requestContext.getResponse();
            JSONObject jsonObject = new JSONObject(resultMap);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return null;
        }
        Map resultMap = null;
        if (tempUrls[4].equals("admin")) {
            resultMap= restTemplate.getForObject(USER_URL + "adminLoginCheck?token=" + token, Map.class);
        } else {
            resultMap = restTemplate.getForObject(USER_URL + "checkLoginInfo?token=" + token, Map.class);
        }
        if ((boolean) resultMap.get("result")) {
            return null;
        } else {
            requestContext.setSendZuulResponse(false);
            HttpServletResponse response = requestContext.getResponse();
            JSONObject jsonObject = new JSONObject(resultMap);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return null;
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
        if (tempUrls[3].equals(IMAGE)) {
            return false;
        }
        if (tempUrls[3].equals(VIDEO)) {
            return false;
        }
        //    /admin/login  /admin/adminlogin放行
        if (tempUrls[3].equals(USER)) {
            if (tempUrls[4].equals("admin")) {
                if (tempUrls.length > 5) {
                    if (tempUrls[5].equals("login") || tempUrls[5].equals("adminLogin")) {
                        return false;
                    }
                }
            }
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

        return true;
    }

}
