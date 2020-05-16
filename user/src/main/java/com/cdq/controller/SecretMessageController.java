package com.cdq.controller;

import com.cdq.execution.SecretMessageExecution;
import com.cdq.model.SecretMessage;
import com.cdq.model.User;
import com.cdq.service.SecretMessageService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/29 9:28
 * @description：私信接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping(value = "/sm")
public class SecretMessageController {

    @Autowired
    private SecretMessageService secretMessageService;

    /**
     * 所有未读信息数量接口
     *
     * @return
     */
    @RequestMapping(value = "/ugamn", method = RequestMethod.POST)
    public Map getAllMessageNumber(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取token中的userId
        User user = ObjectUtil.getUserId(request);
        //调用service层
        SecretMessageExecution result = secretMessageService.getAllMessageNum(user.getUserId());
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put("messageNum",result.getCount());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }


    /**
     * 未读消息数量接口
     *
     * @return
     */
    @RequestMapping(value = "/ugmn", method = RequestMethod.POST)
    public Map getMessageNumber(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取token中的userId
        SecretMessage secretMessage = new SecretMessage();
        secretMessage.setToUser(ObjectUtil.getUserId(request));
        //调用service层
        SecretMessageExecution result = secretMessageService.getSecreMessageNum(secretMessage);
        resolveResult(result, modelMap, 1);
        return modelMap;
    }

    /**
     * 未读消息接口
     *
     * @return
     */
    @RequestMapping(value = "/ugurm", method = RequestMethod.POST)
    public Map getUnReadMessage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //参数转换
        SecretMessage secretMessage = createSM(request);
        //调用service层
        SecretMessageExecution result = secretMessageService.getSMByFromUser(secretMessage);
        resolveResult(result, modelMap, 2);
        return modelMap;
    }

    /**
     * 历史记录接口
     *
     * @return
     */
    @RequestMapping(value = "/ughm", method = RequestMethod.POST)
    public Map getHistoryMessage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //参数转换
        SecretMessage secretMessage = createSM(request);
        //调用service层
        SecretMessageExecution result = secretMessageService.getHistoryMessage(secretMessage);
        resolveResult(result, modelMap, 2);
        return modelMap;
    }

    /**
     * 批量修改消息状态值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ucms", method = RequestMethod.POST)
    public Map<String, Object> changeMessageState(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String listStr = HttpServletRequestUtil.getString(request, "listStr");
        List<Integer> list = (List<Integer>) ObjectUtil.toPojo(listStr, List.class);
        //调用service层
        SecretMessageExecution result = secretMessageService.changeMessageState(list, (byte) 1);
        //批量修改消息状态不用返回结果
        return modelMap;
    }

    /**
     * 批量修改系统通知状态值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ucss", method = RequestMethod.POST)
    public Map<String, Object> changeSystemState(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String listStr = HttpServletRequestUtil.getString(request, "listStr");
        List<Integer> list = (List<Integer>) ObjectUtil.toPojo(listStr, List.class);
        //调用service层
        SecretMessageExecution result = secretMessageService.changeSystemState(list, (byte) 1);
        //批量修改消息状态不用返回结果
        return modelMap;
    }

    /**
     * 添加私信记录接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/uasm", method = RequestMethod.POST)
    public Map<String, Object> addSecretMessage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //参数装换
        SecretMessage secretMessage = (SecretMessage) ObjectUtil.toPojo(
                HttpServletRequestUtil.getString(request, ConstansUtil.SM_STR),
                SecretMessage.class);
        //获取token中的userId
        User fromUser = ObjectUtil.getUserId(request);
        secretMessage.setFromUser(fromUser);
        //调用service层
        SecretMessageExecution result = secretMessageService.addMessage(secretMessage);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    private SecretMessage createSM(HttpServletRequest request) {
        User fromUser = ObjectUtil.getFromUserId(request);
        User toUser = ObjectUtil.getUserId(request);
        SecretMessage secretMessage = new SecretMessage();
        secretMessage.setToUser(toUser);
        secretMessage.setFromUser(fromUser);
        return secretMessage;
    }

    private void resolveResult(SecretMessageExecution result, Map modelMap, int code) {
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
            if (code == 1) {
                modelMap.put(ConstansUtil.MESSAGE_NUMBER_LIST, result.getMessageNumberList());
                modelMap.put("systemMessageList",result.getSystemMessageList());
            }
            if (code == 2) {
                modelMap.put(ConstansUtil.SECRET_MESSAGE_LIST, result.getSecretMessageList());
            }
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
    }

}
