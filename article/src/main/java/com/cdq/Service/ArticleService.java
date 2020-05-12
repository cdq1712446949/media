package com.cdq.Service;


import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;

import java.util.List;

public interface ArticleService {

    /**
     * 获取文章列表
     * @param article
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ArticleExecution getArticleList(Article article, int pageIndex, int pageSize);

    /**
     * 添加文章记录
     * @param article
     * @return
     */
    ArticleExecution addArticle(Article article);

    /**
     * 修改文章记录
     * @param article
     * @return
     */
    ArticleExecution changeArticle(Article article);

    /**
     * 修改文章状态
     * @param article
     * @return
     */
    ArticleExecution changeArticleStatus(Article article);

    /**
     * 通过id查询文章记录
     * @param article
     * @return
     */
    ArticleExecution getArticleById(Article article);

    /**
     * 通过userId获取最新发布的文章记录
     * @param article
     * @return
     */
    ArticleExecution getNewArticleByUserId(Article article);

    /**
     * 获取关注文章
     * @param userId
     * @param indexPage
     * @param pageSize
     * @return
     */
    ArticleExecution getAttArticle(String userId , int indexPage, int pageSize);

    /**
     * 获取视频文章，根据热度排序
     * @return
     */
    ArticleExecution getVideoArticle(Article article,int indexPage,int pageSize);

    /**
     * 删除文章记录
     * @param articleId
     * @param userId
     * @return
     */
    ArticleExecution deleteArticle(Integer articleId,String userId);

    /**
     * 查询今日新增文章数量
     * @param article
     * @return
     */
    ArticleExecution getIndexNumber(Article article);

    /**
     * 批量修改文章记录状态值
     * @param list
     * @param status
     * @return
     */
    ArticleExecution changeSatatuses(List<Integer> list,byte status);

    /**
     * 批量删除文章记录
     * @param list
     * @return
     */
    ArticleExecution delArticles(List<Integer> list);

}
