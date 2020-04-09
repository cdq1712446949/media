package com.cdq.service;


import com.cdq.execution.UserExecution;
import com.cdq.model.User;

public interface UserService {

    /**
     * 用户登录接口
     * @param user
     * @return
     */
    UserExecution login(User user);

    /**
     * 根据userId获取用户信息（博客数量等）
     * @param user
     * @return
     */
    UserExecution getUerInfo(User user);



}
