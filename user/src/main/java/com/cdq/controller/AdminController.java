package com.cdq.controller;

import com.alibaba.fastjson.JSON;
import com.cdq.execution.UserExecution;
import com.cdq.model.User;
import com.cdq.service.UserService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.JsonUtil;
import com.cdq.util.JwtUtil;
import com.cdq.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletResponse response, String username, String password) {
        //定义返回给前端的map
        Map<String, Object> modelMap = new HashMap<>();
        //接收前端参数
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        //添加角色属性，限制用户角色为管理员
        user.setUserRole(ConstansUtil.USER_ADMIN);
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

}
