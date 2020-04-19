package com.cdq.service;

import com.cdq.Service.ArticleService;
import com.cdq.dao.BaseTest;
import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;
import com.cdq.model.ArticleType;
import com.cdq.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 10:14
 * @description：cehsi
 * @modified By：
 * @version: 1.0.1
 */
public class ArticleServiceTesst extends BaseTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testAddArticle(){
        Article article = new Article();
        ArticleType articleType =new ArticleType();
        User user = new User();
        articleType.setArticleTypeId((short) 22);
        user.setUserId("18271579008757346");
        article.setArticleType(articleType);
        article.setUser(user);
        article.setArticleContent("sssss");
        article.setArticleCreateTime(new Date());
        ArticleExecution articleExecution = articleService.addArticle(article);
        System.out.println(article.getArticleId());
    }

}
