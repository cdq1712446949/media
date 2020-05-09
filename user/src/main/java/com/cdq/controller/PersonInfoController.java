package com.cdq.controller;

import com.cdq.execution.PersonInfoExecution;
import com.cdq.model.PersonInfo;
import com.cdq.model.User;
import com.cdq.service.PersonInfoService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/9 16:42
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/perInfo")
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    /**
     * 查询账号设置接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/ugpi" , method = RequestMethod.POST)
    public Map getPersonInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(user.getUserId());
        //调用service层
        PersonInfoExecution result = personInfoService.selectPersonInfo(personInfo);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.PERSON_INFO,result.getPersonInfo());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 修改账户设置
     * @param request
     * @return
     */
    @RequestMapping(value = "/udpi" , method = RequestMethod.POST)
    public Map editPersonInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //接收参数
        User user = ObjectUtil.getUserId(request);
        PersonInfo personInfo = (PersonInfo) ObjectUtil.toPojo(HttpServletRequestUtil.getString(
                request,ConstansUtil.PERSON_INFO_STR), PersonInfo.class);
        personInfo.setUserId(user.getUserId());
        //调用service层
        PersonInfoExecution result = personInfoService.editPersonInfo(personInfo);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

}
