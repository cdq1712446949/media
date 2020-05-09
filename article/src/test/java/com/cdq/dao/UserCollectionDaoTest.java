package com.cdq.dao;


import com.cdq.model.Article;
import com.cdq.model.User;
import com.cdq.model.UserCollection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserCollectionDaoTest extends BaseTest {

    @Autowired
    private UserCollectionDao userCollectionDao;

    @Test
    public void testSelectUserCollection(){
        UserCollection userCollection=new UserCollection();
        User user=new User();
        user.setUserId("19980818");
        userCollection.setUser(user);
        List<UserCollection> list = userCollectionDao.selectUserCollection(userCollection,0,10);
        System.out.println(list.size());
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
        int result= userCollectionDao.updateUserCollection(userCollection);
    }

}
