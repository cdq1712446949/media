package com.cdq.Service;


import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;

public interface ArticleService {

    /**
     * 获取文章列表
     * @param article
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ArticleExecution getArticleList(Article article, int pageIndex, int pageSize, String sortColumn, String ad);

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

}
