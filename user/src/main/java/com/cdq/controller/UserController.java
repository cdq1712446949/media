package com.cdq.controller;

import com.alibaba.fastjson.JSON;
import com.cdq.execution.UserExecution;
import com.cdq.model.User;
import com.cdq.service.UserService;
import com.cdq.util.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.management.modelmbean.ModelMBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:57
 * @description：用户模块数据接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class UserController {



    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/getUser.do")
    public R getUser() {
        return R.success("UserController.getUser()");
    }

    /**
     * 用户登录方法
     * 业务逻辑：
     * 1.查询用户名是否存在，如果存在检查密码是否正确，如果不存在返回提示信息，如果密码错误返回提示信息
     * 2.密码正确后根据用户信息生成token加密后返回给用户，并提示登陆成功
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public Map<String, Object> login(HttpServletResponse response, String username, String password) {
        //定义返回给前端的map
        Map<String, Object> modelMap = new HashMap<>();
        //接收前端参数
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        //调用service层查验参数，连接数据库验证账号密码
        UserExecution result = userService.login(user);
        //根据service返回结果生成返回给前端的数据
        if (result.getState() == 0) {
            //状态码如果是0，表示账号密码正确
            //账号密码正确生成jwt返回给前端
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
        return modelMap;
    }

    /**
     * 用户注册接口
     */
    @RequestMapping("/register")
    @HystrixCommand(
            commandKey = "register",
            fallbackMethod = "registerErro",
            commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            }
    )
    public Map<String, Object> register(HttpServletRequest request) {
        Map<String ,Object> modelMap = new HashMap<>();
        //参数转换
        User user = (User) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request,ConstansUtil.USER_STR),User.class);
        //生成userId
        user.setUserId(UserIdUtil.createUserId());
        //调用service层
        UserExecution result = userService.register(user);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.USER_ID,user.getUserId());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 降级方法访问控制修饰类型，返回类型，参数都要和原方法一致
     *
     * @param user
     * @return
     */
    public Object registerErro(String user) {
        return R.error("服务异常，请重试！");
    }
}
