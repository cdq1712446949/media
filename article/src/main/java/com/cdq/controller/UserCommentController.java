package com.cdq.controller;

import com.cdq.Service.UserCommentService;
import com.cdq.dao.UserCommentDao;
import com.cdq.execution.UserCommentExecution;
import com.cdq.model.Article;
import com.cdq.model.User;
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
    @Autowired
    private UserCommentDao userCommentDao;

    /**
     * 用户发表评论接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uauc", method = RequestMethod.POST)
    public Map addUserComment(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //前端接收参数
        UserComment userComment = (UserComment) ObjectUtil.toPojo(
                HttpServletRequestUtil.getString(request, ConstansUtil.USER_COMMENT_STR), UserComment.class);
        //通过token获取用户id
        User user = ObjectUtil.getUserId(request);
        try {
            User user1 = userCommentDao.queryUserStatus(user);
            if (user1.getUserStatus() == 1) {
                modelMap.put(ConstansUtil.SUCCESS, false);
                modelMap.put(ConstansUtil.ERRMSG, "您没有发送评论的权限");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, e.getMessage());
            return modelMap;
        }
        userComment.setFromUser(user);
        //调用service层
        UserCommentExecution result = userCommentService.addUserComment(userComment);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 通过articleId获取评论列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ugucl", method = RequestMethod.POST)
    public Map<String, Object> getUserCommentList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int indexPage = 1;
        //参数转换
        String articleId = HttpServletRequestUtil.getString(request, ConstansUtil.ARTICLE_ID);
        try {
            indexPage = HttpServletRequestUtil.getInt(request, ConstansUtil.INDEX_PAGE);
        } catch (Exception e) {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, e.getMessage());
            return modelMap;
        }
        if (articleId != null && !ConstansUtil.EMPTY_STR.equals(articleId)) {
            Article article = new Article();
            article.setArticleId(Integer.valueOf(articleId));
            UserComment userComment = new UserComment();
            userComment.setArticle(article);
            //调用service层
            UserCommentExecution result = userCommentService.getUserCommentList(userComment, indexPage, 100);
            if (result.getState() == 0) {
                modelMap.put(ConstansUtil.SUCCESS, true);
                modelMap.put(ConstansUtil.COMMENT_LIST, result.getUserCommentList());
            } else {
                modelMap.put(ConstansUtil.SUCCESS, false);
                modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
            }
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, "请选择文章");
        }
        return modelMap;
    }

    /**
     * 评论删除接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uducomm", method = RequestMethod.POST)
    public Map delUserComment(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        Integer commentId = HttpServletRequestUtil.getInt(request, "commentId");
        UserCommentExecution result = userCommentService.delUserComment(commentId, user);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

}
