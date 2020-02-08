package com.cdq.media.controller.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 13:44
 * @description：controller层，负责返回用户需要的所有页面
 * @modified By：
 * @version: 1.0.1
 */
@Controller
public class SuperUserController {
    @RequestMapping(value = "/index" , method = RequestMethod.GET)
    public String index(){
        return "user/index";
    }

}
