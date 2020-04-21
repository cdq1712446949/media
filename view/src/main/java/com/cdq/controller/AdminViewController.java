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

    @RequestMapping(value = "/articleList",method = RequestMethod.GET)
    public String articleList(){
        return "admin/list_article";
    }

    @RequestMapping(value = "/adverList",method = RequestMethod.GET)
    public String adverList(){
        return "admin/list_adver";
    }

    @RequestMapping(value = "/noticeList",method = RequestMethod.GET)
    public String noticeList(){
        return "admin/list_notice";
    }

    @RequestMapping(value = "/reportList",method = RequestMethod.GET)
    public String reportList(){
        return "admin/list_report";
    }

    @RequestMapping(value = "/reasonList",method = RequestMethod.GET)
    public String reasonList(){
        return "admin/list_report_reason";
    }

    @RequestMapping(value = "/artiTypeList",method = RequestMethod.GET)
    public String artiTypeList(){
        return "admin/list_type";
    }

    @RequestMapping(value = "/userData",method = RequestMethod.GET)
    public String userData(){
        return "admin/data_user";
    }

    @RequestMapping(value = "/articleData",method = RequestMethod.GET)
    public String articleData(){
        return "admin/data_article";
    }

    @RequestMapping(value = "/imgData",method = RequestMethod.GET)
    public String imgData(){
        return "admin/data_img";
    }

    @RequestMapping(value = "/addAdver",method = RequestMethod.GET)
    public String addAdver(){
        return "admin/add_adver";
    }

    @RequestMapping(value = "/addNotice",method = RequestMethod.GET)
    public String addNotice(){
        return "admin/add_notice";
    }

}
