package com.cdq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/8 16:26
 * @description：返回后台管理界面
 * @modified By：
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "admin/login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "admin/index";
    }



}
