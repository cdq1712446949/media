package com.cdq.controller;

import com.cdq.Service.UserCommentService;
import com.cdq.execution.UserCommentExecution;
import com.cdq.model.UserComment;
import com.cdq.until.ConstansUtil;
import com.cdq.until.HttpServletRequestUtil;
import com.cdq.until.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/24 21:58
 * @description：用户评论借口而
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class UserCommentController {

    @Autowired
    private UserCommentService userCommentService;

    @RequestMapping(value = "/uauc" , method = RequestMethod.POST)
    public Map addUserComment(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //前端接收参数
        UserComment userComment = (UserComment) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request,ConstansUtil.USER_COMMENT_STR),UserComment.class);
        //通过token获取用户id
        String userId = ObjectUtil.getUserId(request).getUserId();
        if (ConstansUtil.EMPTY_STR.equals(userId)){
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,"当前登录已过期，请重新登录");
            return modelMap;
        }
        //调用service层
        UserCommentExecution result = userCommentService.addUserComment(userComment);
        if (result.getState() == 0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

}
