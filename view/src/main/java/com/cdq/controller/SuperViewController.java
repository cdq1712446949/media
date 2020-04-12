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

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "user/test";
    }

}
