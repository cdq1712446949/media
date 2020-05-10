package com.cdq.controller;

import com.cdq.Service.ThumbsUpService;
import com.cdq.execution.ThumbsUpExecution;
import com.cdq.model.Article;
import com.cdq.model.ThumbsUp;
import com.cdq.model.User;
import com.cdq.until.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdq
 * created on 2019.9.15
 * 点赞操作数据接口
 */
@RestController
public class ThumbsUpController {

    @Autowired
    private ThumbsUpService thumbsUpService;

    /**
     * 点赞管理
     *
     * @param articleId
     * @param status
     * @return
     */
    @RequestMapping(value = "/thumbsupmanage", method = RequestMethod.POST)
    public Map<String, Object> thumbsUpManage(HttpServletRequest request, String articleId, Byte status) {
        Map<String, Object> modelMap = new HashMap<>();
        //前端参数处理
        ThumbsUp thumbsUp = new ThumbsUp();
        User user = ObjectUtil.getUserId(request);
        Article article = new Article();
        article.setArticleId(Integer.valueOf(articleId));
        thumbsUp.setUser(user);
        thumbsUp.setArticle(article);
        thumbsUp.setUpStatus(status);
        //service层调用
        try {
            ThumbsUpExecution thumbsUpExecution = thumbsUpService.thumbsManage(thumbsUp);
            if (thumbsUpExecution.getState() == 0) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", thumbsUpExecution.getStateInfo());
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请确认操作是否合理");
        }
        return modelMap;
    }

}
