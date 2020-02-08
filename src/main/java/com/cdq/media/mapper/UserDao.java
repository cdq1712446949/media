package com.cdq.media.mapper;

import com.cdq.media.model.ThumbsCollection;
import com.cdq.media.model.User;
import com.cdq.media.model.UserInfo;
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

    /**
     * 根据用户id和articleId查询该用户是否点赞或者收藏了该文章
     * @param thumbsCollection
     * @return
     */
    ThumbsCollection queryThumbsCollection(ThumbsCollection thumbsCollection);

    /**
     * 注册
     * service校验参数，String类型值不能为空
     * @param user
     * @return
     */
    boolean registerUser(User user);

}
