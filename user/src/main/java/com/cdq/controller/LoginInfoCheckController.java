package com.cdq.controller;

import com.cdq.model.User;
import com.cdq.service.UserService;
import com.cdq.util.ConstansUtil;
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
                        modelMap.put(ConstansUtil.RESULT, true);
                    }
                } else {
                    //TODO 向用户发送系统通知
                    redisUtil.del(JwtUtil.TOKEN + id);
                    modelMap.put(ConstansUtil.RESULT, false);
                    modelMap.put(ConstansUtil.REDIRECT, "/user/login");
                    modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RELOGIN);
                }
            } else {
                modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RELOGIN);
                modelMap.put(ConstansUtil.RESULT, false);
                modelMap.put(ConstansUtil.REDIRECT, "http://media.com/media/login");
            }
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            String id = claims.getId();
            String jwt = JwtUtil.createJWT(id, (String) claims.get("sub"), 1200 * 1000);
            redisUtil.set(JwtUtil.TOKEN + id, jwt, 3600 * 1, TimeUnit.SECONDS);
            modelMap.put("token", jwt);
            modelMap.put(ConstansUtil.RESULT, false);
            modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RESEND);
        } finally {
            return modelMap;
        }
    }

    /**
     * 检查用户登录信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/adminLoginCheck")
    public Map<String, Object> adminCheckLoginInfo(String token) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证token是否被篡改
        //1.获取token中的id,检查缓存中是否存在该记录
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String id = claims.getId();
            User user = new User();
            //检查该user的角色值
            int role = userService.selectInfo(user).getUser().getUserRole();
            if (role != 0 && role != 1) {
                modelMap.put(ConstansUtil.RESULT, false);
                modelMap.put(ConstansUtil.ERRMSG, "您没有操作权限");
                return modelMap;
            }
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
                        modelMap.put(ConstansUtil.RESULT, true);
                    }
                } else {
                    //TODO 向用户发送系统通知
                    redisUtil.del(JwtUtil.TOKEN + id);
                    modelMap.put(ConstansUtil.RESULT, false);
                    modelMap.put(ConstansUtil.REDIRECT, "http://media.com/media/admin/login");
                    modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RELOGIN);
                }
            } else {
                modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RELOGIN);
                modelMap.put(ConstansUtil.RESULT, false);
                modelMap.put(ConstansUtil.REDIRECT, "http://media.com/media/admin/login");
            }
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            String id = claims.getId();
            String jwt = JwtUtil.createJWT(id, (String) claims.get("sub"), 1200 * 1000);
            redisUtil.set(JwtUtil.TOKEN + id, jwt, 3600 * 1, TimeUnit.SECONDS);
            modelMap.put(ConstansUtil.TOKEN, jwt);
            modelMap.put(ConstansUtil.RESULT, false);
            modelMap.put(ConstansUtil.STATE_CODE, JwtUtil.RESEND);
        } finally {
            return modelMap;
        }
    }

}
