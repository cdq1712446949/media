package com.cdq.dao;


import com.cdq.model.Article;
import com.cdq.model.User;
import com.cdq.model.UserCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserCollectionDaoTest extends BaseTest {

    @Autowired
    private UserCollectionDao userCollectionDao;

    @Test
    public void testSelectUserCollection(){

    }

    @Test
    public void testDuplicateCheck(){
        UserCollection userCollection=new UserCollection();
        User user=new User();
        Article article=new Article();
        user.setUserId("19980818");
        article.setArticleId(4);
        userCollection.setUser(user);
        userCollection.setArticle(article);
        UserCollection userCollection1=userCollectionDao.duplicateCheck(userCollection);
    }

    @Test
    public void testInsertUserCollection(){
        UserCollection userCollection=new UserCollection();
        User user=new User();
        Article article=new Article();
        user.setUserId("19980818");
        article.setArticleId(4);
        userCollection.setUser(user);
        userCollection.setArticle(article);
        userCollection.setCollectionCreateTime(new Date());
        int result=userCollectionDao.insertUserCollection(userCollection);
    }

    @Test
    public void testUpdateUserCollection(){
        UserCollection userCollection=new UserCollection();
        User user=new User();
        Article article=new Article();
        user.setUserId("19980818");
        article.setArticleId(4);
        userCollection.setUser(user);
        userCollection.setArticle(article);
        userCollection.setCollectionStatus((byte) -1);
        int result= userCollectionDao.updateUserCollection(userCollection);
    }

}
