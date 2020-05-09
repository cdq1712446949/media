package com.cdq.Service.impl;

import com.cdq.Service.UserCommentService;
import com.cdq.dao.UserCommentDao;
import com.cdq.enums.UserCommentStateEnum;
import com.cdq.execution.UserCommentExecution;
import com.cdq.model.UserComment;
import com.cdq.until.ConstansUtil;
import com.cdq.until.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserCommentServiceImpl implements UserCommentService {

    @Autowired
    private UserCommentDao userCommentDao;

    /**
     * 获取用户评论列表
     * 1.校验参数(article.articleId)
     *
     * @param userComment 为article.articleId赋值
     * @return
     */
    @Override
    public UserCommentExecution getUserCommentList(UserComment userComment, int pageIndex, int pageSize) {
        //校验参数
        if (userComment.getArticle() == null) {
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_ARTICLEID);
        }
        if (userComment.getArticle().getArticleId() == null || userComment.getArticle().getArticleId() == 0) {
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_ARTICLEID);
        }
        //调用dao层接口获取数据
        try {
            //把页码转化为行数索引
            int rowIndex = PageUtil.pageToRowIndex(pageIndex, pageSize);
            List<UserComment> userComments = userCommentDao.queryUserCommentList(userComment, rowIndex, pageSize);
            return new UserCommentExecution(UserCommentStateEnum.SUCCESS, userComments);
        } catch (Exception e) {
            return new UserCommentExecution(UserCommentStateEnum.INNER_ERROR);
        }
    }

    /**
     *发表评论接口
     * @param userComment
     * @return
     */
    @Override
    public UserCommentExecution addUserComment(UserComment userComment) {
        //参数校验
        if (userComment.getFromUser()==null||userComment.getFromUser().getUserId()==null){
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_FROM_USER);
        }
        if (userComment.getArticle()==null||userComment.getArticle().getArticleId()==null){
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_ARTICLEID);
        }
        if (userComment.getUserCommentContent()==null||userComment.getUserCommentContent().equals(ConstansUtil.EMPTY_STR)){
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_COMMENT_CONTENT);
        }
        userComment.setUserCommentCreateTime(new Date());
        int result = userCommentDao.insertUserComment(userComment);
        if (result == 0) {
            return new UserCommentExecution(UserCommentStateEnum.INNER_ERROR);
        } else {
            return new UserCommentExecution(UserCommentStateEnum.SUCCESS);
        }

    }

    /**
     * 删除接口
     *
     * @param userCommentId
     * @return
     */
    @Override
    public UserCommentExecution delUserComment(Integer userCommentId) {
        //参数校验
        if (userCommentId == null) {
            return new UserCommentExecution(UserCommentStateEnum.EMPTY_ARTICLEID);
        }
        //组合参数
        UserComment userComment = new UserComment();
        userComment.setUserCommentId(userCommentId);
        //调用dao层
        int result = userCommentDao.delUserComment(userComment);
        if (result != 0) {
            return new UserCommentExecution(UserCommentStateEnum.SUCCESS);
        }else{
            return new UserCommentExecution(UserCommentStateEnum.INNER_ERROR);
        }
    }
}
