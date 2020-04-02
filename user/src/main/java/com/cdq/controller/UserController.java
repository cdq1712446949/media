package com.cdq.controller;

import com.alibaba.fastjson.JSON;
import com.cdq.util.JwtUtil;
import com.cdq.util.R;
import com.cdq.util.RedisUtil;
import com.cdq.util.UserIdUtil;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login")
    public R login(HttpServletRequest request, HttpServletResponse response) {
        //接收前端参数

        //调用service层查验参数

        //返回结果
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userName", "root");
        userMap.put("role", 1);
        userMap.put("level", 100);
        userMap.put("userId", 10001);
        //生成JWT
        try {
            String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(userMap), 3600 * 24);
            //RSA算法加密jwt
//            Map<String, String> map = RSAUtils.generateKeyPair();
//            String publicKey = map.get("publicKey");
//            String privateKey = map.get("privateKey");
//            String sKey = RSAUtils.encrypt(publicKey, jwt);
//            Map<String, Object> tempMap = new HashMap<>();
//            tempMap.put("token", sKey);
//            tempMap.put("privateKey", privateKey);
//            tempMap.put("publicKey", publicKey);
            //jwt存储到缓存中
            String tokenId = UserIdUtil.createKey();
            redisUtil.set(tokenId, userMap);
            //添加cookie

            //设置返回信息set("tokenId", tokenId)set("token", sKey).set("publicKey", publicKey)
            return R.success().set("token", jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @RequestMapping("/register")
    @HystrixCommand(
            commandKey = "register",
            fallbackMethod = "registerErro",
            commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled" , value = "true"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" , value = "3000")
            }
    )
    public Object register (String user) {
        Map<String,Object> tempMap = (Map)restTemplate.getForObject("http://CLIENT-ARTICLE/getArticle.do",Object.class);
        return R.success(tempMap.toString());
    }

    /**
     * 降级方法访问控制修饰类型，返回类型，参数都要和原方法一致
     * @param user
     * @return
     */
    public Object registerErro (String user)   {
        return R.error("服务异常，请重试！");
    }
}
