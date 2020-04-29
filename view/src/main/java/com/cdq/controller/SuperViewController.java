package com.cdq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 9:37
 * @description：返回前端界面
 * @modified By：
 * @version: 1.0.1
 */
@Controller
public class SuperViewController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getIndex(){
        return "user/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "user/login";
    }

    @RequestMapping(value = "/articleEdit",method = RequestMethod.GET)
    public String articleEdit(){
        return "user/articleEdit";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "user/test";
    }

    @RequestMapping(value = "/article",method = RequestMethod.GET)
    public String article(){
        return "user/article";
    }

    @RequestMapping(value = "/video",method = RequestMethod.GET)
    public String video(){
        return "user/video";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(){
        return "user/search";
    }

    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public String message(){
        return "user/message";
    }

    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public String me(){
        return "user/me";
    }

    @RequestMapping(value = "/artideti",method = RequestMethod.GET)
    public String articleDetial(){
        return "user/article_detial";
    }

    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String register(){
        return  "user/register";
    }

    @RequestMapping(value = "/report" , method = RequestMethod.GET)
    public String report(){
        return  "user/report";
    }

    @RequestMapping(value = "/chat" , method = RequestMethod.GET)
    public String chat(){
        return  "user/chat";
    }

}
