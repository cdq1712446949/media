package com.cdq.Service;

import com.cdq.execution.ArticleTypeExecution;
import com.cdq.model.ArticleType;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/9 18:48
 * @description：文章类型service层接口
 * @modified By：
 * @version: $
 */
public interface ArticleTypeService {

    /**
     * 根据属性的有无，组合查询文章类型列表
     * @param articleType
     * @return
     */
    ArticleTypeExecution getArticleTypeList(ArticleType articleType);

    /**
     * 修改文章类型
     * @param articleType
     * @return
     */
    ArticleTypeExecution updateArticleType(ArticleType articleType);

    /**
     * 获取一级文章类型列表(添加缓存)
     * @return
     */
    ArticleTypeExecution getFirstArticleTypeList();

    /**
     * 通过id获取文章类型记录
     * @param articleType
     * @return
     */
    ArticleTypeExecution getArticleTypeById(ArticleType articleType);

    /**
     *
     * @param articleType
     * @return
     */
    ArticleTypeExecution getArticleTypeTwoLevel();

}
