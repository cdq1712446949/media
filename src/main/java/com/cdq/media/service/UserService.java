package com.cdq.media.service;

import com.cdq.media.execution.UserExecution;
import com.cdq.media.model.ThumbsCollection;
import com.cdq.media.model.User;

public interface UserService {

    /**
     * 用户登录接口
     * @param user
     * @return
     */
    UserExecution login(User user);

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    UserExecution register(User user);

    /**
     * 根据userId获取用户信息（博客数量等）
     * @param user
     * @return
     */
    UserExecution getUerInfo(User user);

    /**
     * 获取登录用户对当前文章的点赞和收藏状态
     * @param thumbsCollection
     * @return
     */
    UserExecution getThumbsCollection(ThumbsCollection thumbsCollection);

}
