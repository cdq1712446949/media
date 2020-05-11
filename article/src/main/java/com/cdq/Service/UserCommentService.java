package com.cdq.Service;


import com.cdq.execution.UserCommentExecution;
import com.cdq.model.User;
import com.cdq.model.UserComment;

/**
 * 用户评论service接口
 * @author cdq
 * created on 2019.9.11
 */
public interface UserCommentService {

    /**
     * 获取用户评论列表
     * @param userComment 为article.articleId赋值
     * @return 结果
     */
    UserCommentExecution getUserCommentList(UserComment userComment, int pageIndex, int pageSize);

    /**
     * 用户发表评论接口
     * @param userComment
     * @return
     */
    UserCommentExecution addUserComment(UserComment userComment);

    /**
     * 删除评论记录接口
     * @param userCommentId
     * @return
     */
    UserCommentExecution delUserComment(Integer userCommentId, User user);

}
