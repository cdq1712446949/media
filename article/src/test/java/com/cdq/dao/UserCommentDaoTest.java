package com.cdq.dao;

import com.cdq.model.Article;
import com.cdq.model.UserComment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserCommentDaoTest extends BaseTest {

    @Autowired
    private UserCommentDao userCommentDao;

    @Test
    public void testQueryUserCommentList(){
        UserComment userComment=new UserComment();
        Article article=new Article();
        article.setArticleId(1);
        userComment.setArticle(article);
        List<UserComment> list=userCommentDao.queryUserCommentList(userComment,0,10);
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).getUserCommentContent());
        }
    }

}
