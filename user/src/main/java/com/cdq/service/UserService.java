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

    /**
     * 超级管理员登录方法
     * @param user
     * @return
     */
    UserExecution adminLogin(User user);

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    UserExecution register(User user);

    /**
     * 查询用户信息(粉丝数量以及文章数量)接口
     * @param user
     * @return
     */
    UserExecution getUserInfo(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    UserExecution changeUserInfo(User user);

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    UserExecution selectInfo(User user);

}
