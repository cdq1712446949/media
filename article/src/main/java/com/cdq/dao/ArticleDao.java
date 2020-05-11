package com.cdq.dao;

import com.cdq.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.util.List;

@Component("articleDao")
public interface ArticleDao {

    /**
     * * 获取文章列表
     * * 根据title,discription模糊查询
     * * 根据id查询，id查询不在组合查询内
     * * 根据作者查询
     *
     * @param article  组合查询条件
     * @param rowIndex 从第几行开始获取
     * @param pageSize 每一页记录的数量
     * @return
     */
    //TODO 修改查询条件（articleDao.xml）
    List<Article> queryArticleList(@Param("article") Article article, @Param("rowIndex") @Min(0) int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 添加文章记录
     *
     * @param article
     * @return
     */
    int insertArticle(Article article);

    /**
     * 修改文章记录
     *
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 修改文章状态
     *
     * @param article
     * @return
     */
    int updateArticleStatus(Article article);

    /**
     * 通过id查询文章记录
     *
     * @param article
     * @return
     */
    Article queryArticleById(Article article);

    /**
     * 根据userId获取最新发布的五篇文章
     * 文章状态必须为0才可以获取
     *
     * @param article
     * @return
     */
    List<Article> queryNewArticleByUserId(Article article);

    /**
     * 获取该条件下的文章数量
     *
     * @param article
     * @return
     */
    int queryArticleCount(@Param("article") Article article);

    /**
     * 根据userId获取该用户关注的人的文章
     *
     * @param userId
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Article> queryAttArticle(@Param("userId") String userId, @Param("rowIndex") int rowIndex,
                                  @Param("pageSize") int pageSize);

    /**
     * 查询关注用户文章数量
     *
     * @param userId
     * @return
     */
    int queryAttArticleCount(@Param("userId") String userId);

    /**
     * 获取带有视频的文章列表
     *
     * @param article
     * @return
     */
    List<Article> queryVideoArticle(@Param("article") Article article, @Param("rowIndex") int rowIndex,
                                    @Param("pageSize") int pageSize);

    /**
     * 查询视频文章总数
     * @return
     */
    int queryVideoArticleCount();

    /**
     * 删除文章记录
     */
    int delArticle(Article article);

    /**
     * 查询今日新增文章数量
     * @return
     */
    Integer newArticleNum(Article article);

}
