package com.cdq.dao;


import com.cdq.model.Article;
import com.cdq.model.ThumbsUp;
import com.cdq.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ThumbsUpDaoTest extends BaseTest {

    @Autowired
    private ThumbsUpDao thumbsUpDao;

    @Test
    public void testInsertThumbsUp(){
        ThumbsUp thumbsUp=new ThumbsUp();
        Article article=new Article();
        article.setArticleId(3);
        User user=new User();
        user.setUserId("19980818");
        thumbsUp.setArticle(article);
        thumbsUp.setUpCreateTime(new Date());
        thumbsUp.setUser(user);
//        thumbsUp.set
        try {
            int result=thumbsUpDao.insertThumbsUp(thumbsUp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdateThumbsUp(){
        ThumbsUp thumbsUp=new ThumbsUp();
        thumbsUp.setUpStatus((byte) -1);
        thumbsUp.setThumbsUpId(2);
        thumbsUpDao.updateThumbsUp(thumbsUp);
    }

    @Test
    public void testSelectThumbsUp(){
        ThumbsUp thumbsUp=new ThumbsUp();
        User user=new User();
        Article article=new Article();
        user.setUserId("19980818");
        article.setArticleId(2);
        thumbsUp.setUser(user);
        thumbsUp.setArticle(article);
        ThumbsUp thumbsUp1=thumbsUpDao.selectThumbsUp(thumbsUp);
        thumbsUp.setArticle(article);
//        System.out.println(thumbsUp1.getUpCreateTime());
    }

}
