package com.cdq.controller;

import com.cdq.Service.UserCollectionService;
import com.cdq.execution.AttentionExecution;
import com.cdq.execution.UserCollectionExecution;
import com.cdq.model.Article;
import com.cdq.model.Attention;
import com.cdq.model.User;
import com.cdq.model.UserCollection;
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
 * @date ：Created in 2020/5/8 22:13
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/coll")
public class UserCollectionController {

    @Autowired
    private UserCollectionService userCollectionService;

    /**
     * 通过userId过去收藏列表
     * @param request
     * @return
     */
    @RequestMapping(value="/ugcl",method = RequestMethod.POST)
    public Map getCollectionList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        UserCollection userCollection = new UserCollection();
        userCollection.setUser(user);
        //调用service层
        UserCollectionExecution result = userCollectionService.getCollectionList(userCollection,
                HttpServletRequestUtil.getInt(request, "indexPage"), 10);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
            modelMap.put(ConstansUtil.ATTENTION_LIST, result.getUserCollectionList());
            modelMap.put(ConstansUtil.TOTAL_SIZE,result.getCount());
            modelMap.put(ConstansUtil.TOTAL_PAGE,Math.ceil((double)result.getCount()/10));
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 添加收藏记录
     * @param request
     * @return
     */
    @RequestMapping(value = "/uacoll" , method = RequestMethod.POST)
    public Map addCollection(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        String articleId = HttpServletRequestUtil.getString(request, "articleId");
        User user = ObjectUtil.getUserId(request);
        Article article =new Article();
        article.setArticleId(Integer.valueOf(articleId));
        UserCollection userCollection = new UserCollection();
        userCollection.setUser(user);
        userCollection.setArticle(article);
        //调用service层
        UserCollectionExecution result = userCollectionService.userCollectionManage(userCollection);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 删除收藏记录接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/udcoll" , method = RequestMethod.POST)
    public Map delCollection(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        User user = ObjectUtil.getUserId(request);
        String collectionId = HttpServletRequestUtil.getString(request, "collectionId");
        UserCollection userCollection = new UserCollection();
        userCollection.setCollectionId(Integer.valueOf(collectionId));
        userCollection.setUser(user);
        //调用service层
        UserCollectionExecution result = userCollectionService.delCollection(userCollection);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
        return modelMap;
    }

}
