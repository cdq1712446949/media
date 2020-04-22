package com.cdq.controller;

import com.alibaba.fastjson.JSON;
import com.cdq.execution.UserExecution;
import com.cdq.model.User;
import com.cdq.service.UserService;
import com.cdq.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/19 19:52
 * @description：管理员请求后台接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 管理员登录方法
     * 业务逻辑：
     * 1.查询用户名是否存在，如果存在检查密码是否正确，如果不存在返回提示信息，如果密码错误返回提示信息
     * 2.密码正确后根据用户信息生成token加密后返回给用户，并提示登陆成功
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request) {
        //定义返回给前端的map
        Map<String, Object> modelMap = new HashMap<>();
        //接收前端参数
        User user = (User) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request, ConstansUtil.USER_STR), User.class);
        //添加角色属性，限制用户角色为管理员
        user.setUserRole(ConstansUtil.USER_ADMIN);
        //调用service层查验参数，连接数据库验证账号密码
        UserExecution result = userService.login(user);
        //根据service返回结果生成返回给前端的数据
        resResult(result, modelMap);
        return modelMap;
    }

    /**
     * 超级管理员账号登录接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public Map adminLogin(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        User superUser = (User) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request, ConstansUtil.USER_STR), User.class);
        UserExecution result = userService.adminLogin(superUser);
        resResult(result, modelMap);
        return modelMap;
    }

    /**
     * 退出登录接口
     * 添加验证：要退出的账号是否和当前登录账号一致
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Map logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String userId = HttpServletRequestUtil.getString(request, "userId");
        boolean result = redisUtil.del(JwtUtil.TOKEN + userId);
        if (result){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else {
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put("errMsg","erro");
        }
        return modelMap;
    }

    private void resResult(UserExecution result, Map modelMap) {
        if (result.getState() == 0) {
            try {
                //生成JWT
                String payLoad = JSON.toJSONString(JsonUtil.getJson(result.getUser()));
                String jwt = JwtUtil.createJWT(result.getUser().getUserId(), payLoad, 1200 * 1000);
                //把生成的jwt放入到redis缓存中
                redisUtil.set(JwtUtil.TOKEN + result.getUser().getUserId(), jwt, 3600 * 1, TimeUnit.SECONDS);
                //返回提示信息
                modelMap.put("success", true);
                modelMap.put("userInfo", JsonUtil.getJson(result.getUser()));
                modelMap.put("token", jwt);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名或者密码错误");
        }
    }

}
