package com.cdq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:37
 * @description：文章数据接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;

    public static String USER_URL="http://SERVER-USER";

    /**
     * 调用用户模块检查登录信息
     * @return
     */
    @RequestMapping("/getArticle.do")
    public Map getArticle() throws InterruptedException {
        Map<String,Object> modelMap = new HashMap<>();
        Thread.sleep(5000);
        modelMap.put("success",false);
        modelMap.put("errMsg","操作失败");
        return modelMap;
    }

    @RequestMapping("/getUser.do")
    public Map getUser(){
        Map<String,Object> modelMap = new HashMap<>();
        modelMap.put("userInfo",restTemplate.getForObject(USER_URL+"/getUser.do",Object.class));
        return modelMap;
    }

}
