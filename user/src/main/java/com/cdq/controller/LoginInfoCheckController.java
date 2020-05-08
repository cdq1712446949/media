package com.cdq.controller;

import com.cdq.service.UserService;
import com.cdq.util.JwtUtil;
import com.cdq.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/5 14:11
 * @description：认证中心
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class LoginInfoCheckController {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    /**
     * 检查用户登录信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/checkLoginInfo")
    public Map<String, Object> checkLoginInfo(String token) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证token是否被篡改
        //1.获取token中的id,检查缓存中是否存在该记录
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String id = claims.getId();
            String redisToken = (String) redisUtil.get(JwtUtil.TOKEN + id);
            if (redisToken != null) {
                //判断当前登录用户和redis中的用户token是否相同
                if (redisToken.equals(token)) {
                    //判断是否需要刷新token
                    long localTime = System.currentTimeMillis();
                    Date exp = claims.getExpiration();
                    if (localTime >= exp.getTime()) {
                        //刷新token

                    } else {
                        modelMap.put("result", true);
                    }
                } else {
                    //TODO 向用户发送系统通知
                    redisUtil.del(JwtUtil.TOKEN + id);
                    modelMap.put("result", false);
                    modelMap.put("redirect", "/user/login");
                    modelMap.put("stateCode", JwtUtil.RELOGIN);
                }
            } else {
                modelMap.put("stateCode", JwtUtil.RELOGIN);
                modelMap.put("result", false);
                modelMap.put("redirect", "/user/login");
            }
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            String id = claims.getId();
            String jwt = JwtUtil.createJWT(id, (String) claims.get("sub"), 1200 * 1000);
            redisUtil.set(JwtUtil.TOKEN + id, jwt, 3600 * 1, TimeUnit.SECONDS);
            modelMap.put("token", jwt);
            modelMap.put("result", false);
            modelMap.put("stateCode", JwtUtil.RESEND);
        }finally {
            return modelMap;
        }
    }

}
