package com.cdq.dao;

import com.cdq.model.ArticleType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/9 16:10
 * @description：测试类
 * @modified By：
 * @version: 1.0.1
 */
public class ArticleTypeDaoTest extends BaseTest{

    @Autowired
    private ArticleTypeDao articleTypeDao;

    @Test
    public void testQueryTwoLevel(){
        List<ArticleType> result = articleTypeDao.queryAllTwoLevelArticleType();
        System.out.println(result.size());
    }

}
