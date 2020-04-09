package com.cdq.dao;


import com.cdq.model.User;
import com.cdq.model.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao {

    /**
     * 用户登录查询
     * @param user
     * @return
     */
    User queryUserByUserName(User user);

    /**
     * 获取用户信息(blog_num,funs_num,look_num,comment_num)
     * js计算等级,
     * @param user
     * @return
     */
    UserInfo queryUserInfo(User user);



}
