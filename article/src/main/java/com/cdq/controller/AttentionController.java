package com.cdq.controller;

import com.cdq.Service.AttentionService;
import com.cdq.execution.AttentionExecution;
import com.cdq.model.Attention;
import com.cdq.model.User;
import com.cdq.until.ConstansUtil;
import com.cdq.until.HttpServletRequestUtil;
import com.cdq.until.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/8 15:44
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/atten")
public class AttentionController {

    @Autowired
    private AttentionService attentionService;

    /**
     * 用户通过id获取关注列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ugalbu")
    public Map<String, Object> getAttentionList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        Attention attention = new Attention();
        attention.setAttentionUser(user);
        //调用service层
        AttentionExecution result = attentionService.getAttentionListByUser(attention,
                HttpServletRequestUtil.getInt(request, "indexPage"), 10);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
            modelMap.put(ConstansUtil.ATTENTION_LIST, result.getAttentionList());
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 添加关注记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uaatten")
    public Map<String, Object> addAttention(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String uid1 = HttpServletRequestUtil.getString(request, "attentionUserId");
        User user1 = ObjectUtil.getUserId(request);
        Attention attention = new Attention();
        User user = new User();
        user.setUserId(uid1);
        attention.setAttentionUser(user);
        attention.setAttentedUser(user1);
        //调用service层
        AttentionExecution result = attentionService.addAttention(attention);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 通过userId删除关注记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/udelatten")
    public Map<String, Object> deleteAttention(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        String attentionId = HttpServletRequestUtil.getString(request, "attentionId");
        Attention attention = new Attention();
        attention.setAttentionUser(user);
        attention.setAttentionId(Integer.valueOf(attentionId));
        //调用service层
        AttentionExecution result = attentionService.delAttention(attention);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

}

