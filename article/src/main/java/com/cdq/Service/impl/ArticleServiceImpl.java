package com.cdq.Service.impl;


import com.cdq.Service.ArticleService;
import com.cdq.dao.ArticleDao;
import com.cdq.dao.ArticleTypeDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;
import com.cdq.model.ArticleType;
import com.cdq.until.ConstansUtil;
import com.cdq.until.DateUtil;
import com.cdq.until.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 文章管理service层
 * 组合查询文章记录前必须设置文章状态属性值（0或者-1）
 *
 * @author cdq
 * created on 2019.08.29
 */

@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTypeDao articleTypeDao;

    /**
     * 组合查询文章列表
     * 校验参数：
     * 1.检擦文章类型id是否是二级文章类型id
     * 2.文章状态属性值为0或者-1
     * 3.页码以及数量必须大于等于0
     * 4.sortColumn可选值：goog_num+look_num,article_create_time
     * 5.ad可选值：desc，asc
     * 页码转换为记录行数
     *
     * @param article 参数
     * @param pageIndex 页数
     * @param pageSize 每页数量
     * @return 结果
     */
    @Override
    public ArticleExecution getArticleList(Article article, int pageIndex, int pageSize) {
        //校验参数
        if (article.getArticleStatus() == null) {
            return new ArticleExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //校验文章状态属性值
        if (article.getArticleStatus() != -1 && article.getArticleStatus() != 0) {
            return new ArticleExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //校验页码以及每页记录数量
        if (pageIndex <= 0 && pageSize <= 0) {
            return new ArticleExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //页码转换
        int rowIndex = PageUtil.pageToRowIndex(pageIndex, pageSize);
        //请求数据库查询数据
        try {
            List<Article> articles = articleDao.queryArticleList(article, rowIndex, pageSize);
            return new ArticleExecution(BaseStateEnum.SUCCESS, articles);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArticleExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    /**
     * 添加文章记录
     * 校验参数：userId,articleTypeId,articleTitle,articleDiscription,articleKeyWord,articleContent不能为空
     * 添加创建时间属性值
     * user.userId不能为空
     *
     * @param article 参数
     * @return 结果
     */
    @Override
    @Transactional
    public ArticleExecution addArticle(Article article) {
        //校验参数
        if (article.getUser().getUserId() == null || ConstansUtil.EMPTY_STR.equals(article.getUser().getUserId())) {
            return new ArticleExecution(BaseStateEnum.EMPTY_USER);
        }
        ArticleType articleType = new ArticleType();
        articleType.setArticleTypeId(article.getArticleType().getArticleTypeId());
        //判断类型是否是二级文章类型
        if (articleTypeDao.queryArticleTypeById(articleType).getParentArticleType().getArticleTypeId() == null) {
            return new ArticleExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        if (article.getArticleContent() == null || ConstansUtil.EMPTY_STR.equals(article.getArticleContent())) {
            return new ArticleExecution(BaseStateEnum.EMPTY_INFO);
        }
        //添加创建时间属性值
        article.setArticleCreateTime(new Date());
        //向数据库添加纪录
        try {
            int result = articleDao.insertArticle(article);
            if (result == 1) {
                return new ArticleExecution(BaseStateEnum.SUCCESS);
            } else {
                return new ArticleExecution(BaseStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException("添加文章记录时出错，错误信息：" + e.getMessage());
        }
    }

    /**
     * 修改文章记录
     * 校验参数：id,user.userId不能为空
     *
     * @param article 参数
     * @return 结果
     */
    @Override
    @Transactional
    public ArticleExecution changeArticle(Article article) {
        //校验参数
        if (article.getArticleId() == null || article.getArticleId() == 0) {
            return new ArticleExecution(BaseStateEnum.EMPTY_ID);
        }
        if (article.getUser() == null) {
            return new ArticleExecution(BaseStateEnum.EMPTY_USER);
        }
        if (article.getUser().getUserId() == null && ConstansUtil.EMPTY_STR.equals(article.getUser().getUserId())) {
            return new ArticleExecution(BaseStateEnum.EMPTY_USER);
        }
        //请求数据库，修改记录
        try {
            int result = articleDao.updateArticle(article);
            if (result == 1) {
                return new ArticleExecution(BaseStateEnum.SUCCESS);
            } else {
                return new ArticleExecution(BaseStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException("修改文章记录时出错，错误信息：" + e.getMessage());
        }
    }

    /**
     * 修改文章状态，
     * 如果放在changeArticle方法中，每次执行修改文章状态都会执行大量代码,所以分离出该方法
     *
     * @param article 参数
     * @return 结果
     */
    @Override
    @Transactional
    public ArticleExecution changeArticleStatus(Article article) {
        //校验参数：文章id，文章状态
        if (article.getArticleId() == null || article.getArticleId() == 0) {
            return new ArticleExecution(BaseStateEnum.EMPTY_ID);
        }
        if (article.getArticleStatus() != 0 && article.getArticleStatus() != -1) {
            return new ArticleExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //请求数据库，修改文章状态数据
        try {
            int result = articleDao.updateArticleStatus(article);
            if (result == 1) {
                return new ArticleExecution(BaseStateEnum.SUCCESS);
            } else {
                return new ArticleExecution(BaseStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException("修改文章状态时出错，错误信息：" + e.getMessage());
        }
    }

    /**
     * 通过id获取文章记录
     * @param article 参数
     * @return 结果
     */
    @Override
    public ArticleExecution getArticleById(Article article) {
        //校验参数
        if (article.getArticleId() == 0 || article.getArticleId() == null) {
            return new ArticleExecution(BaseStateEnum.EMPTY_ID);
        }
        //请求数据库，获取数据
        try {
            Article article1 = articleDao.queryArticleById(article);
            article1.setArticleCreateTime(DateUtil.dateFormt(article1.getArticleCreateTime()));
            if (article1.getArticleId() == null) {
                return new ArticleExecution(BaseStateEnum.OBJECT_ISNULL);
            }
            return new ArticleExecution(BaseStateEnum.SUCCESS, article1);
        } catch (Exception e) {
            return new ArticleExecution(BaseStateEnum.OBJECT_ISNULL);
        }
    }

    /**
     * 校验参数
     * 1.user.userId
     *
     * @param article 参数
     * @return 结果
     */
    @Override
    public ArticleExecution getNewArticleByUserId(Article article) {
        //校验参数
        if (article.getUser() == null) {
            return new ArticleExecution(BaseStateEnum.EMPTY_USER);
        }
        if (article.getUser().getUserId() == null || ConstansUtil.EMPTY_STR.equals(article.getUser().getUserId())) {
            return new ArticleExecution(BaseStateEnum.EMPTY_USER);
        }
        //调用dao层获取数据
        try {
            List<Article> list = articleDao.queryNewArticleByUserId(article);
            return new ArticleExecution(BaseStateEnum.SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArticleExecution(BaseStateEnum.INNER_ERROR);
        }
    }
}
