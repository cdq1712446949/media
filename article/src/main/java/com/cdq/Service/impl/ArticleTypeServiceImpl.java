package com.cdq.Service.impl;

import com.cdq.Service.ArticleTypeService;
import com.cdq.dao.ArticleTypeDao;
import com.cdq.enums.ArticleTypeStateEnum;
import com.cdq.execution.ArticleTypeExecution;
import com.cdq.model.ArticleType;
import com.cdq.until.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 文章类型service层实现类
 *
 * @author cdq
 * created on 2019.08.24
 */
@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Autowired
    private ArticleTypeDao articleTypeDao;

    @Override
    public ArticleTypeExecution getArticleTypeList(ArticleType articleType, boolean isFirst) {
        //校验属性信息
        if (articleType.getParentArticleType() == null && isFirst) {
            //添加parentArticle属性值
            articleType.setParentArticleType(new ArticleType());
        }
        List<ArticleType> list = articleTypeDao.queryArticleTypeList(articleType);
        return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS, list);
    }

    /**
     * 修改文章类型数据库记录:文章类型id不能为空,创建时间不能修改
     *
     * @param articleType
     * @return
     */
    @CacheEvict(value = "firstArticleTypeList", allEntries = true)
    @Override
    @Transactional
    public ArticleTypeExecution updateArticleType(ArticleType articleType) {
        //校验参数信息()
        if (articleType.getArticleTypeId() == null) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_ID);
        }
        if (articleType.getArticleTypeCreateTime() != null) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.ILLEGAL_PARAMETER);
        }
        //添加lastEditTime属性值
        articleType.setArticleTypeLastEditTime(new Date());
        //修改数据库记录
        try {
            int result = articleTypeDao.updateArticeType(articleType);
            if (result > 0) {
                return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS);
            } else {
                return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException("修改文章类型记录失败，错误信息：" + e.getMessage());
        }
    }

    @Cacheable(value = "firstArticleTypeList")
    @Override
    public ArticleTypeExecution getFirstArticleTypeList() {
//        System.out.println("获取一级文章类型列表没有调用缓存");
        ArticleType articleType = new ArticleType();
        //添加parentArticle属性值
        articleType.setParentArticleType(new ArticleType());
        List<ArticleType> list = articleTypeDao.queryArticleTypeList(articleType);
        return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS, list);
    }

    @Override
    public ArticleTypeExecution getArticleTypeById(ArticleType articleType) {
        //校验参数
        if (articleType.getParentArticleType().getArticleTypeId() == null || articleType.getParentArticleType().getArticleTypeId() == 0) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_ID);
        }
        //请求数据库获取数据
        try {
            ArticleType articleType1 = articleTypeDao.queryArticleTypeById(articleType);
            return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS, articleType1);
        } catch (Exception e) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
        }
    }

    @Override
    public ArticleTypeExecution getArticleTypeTwoLevel() {
        List<ArticleType> result = articleTypeDao.queryAllTwoLevelArticleType();
        if (result == null) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
        } else {
            return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS, result);
        }
    }

    @Override
    public ArticleTypeExecution addArticleType(ArticleType articleType) {
        //参数校验
        if (articleType == null) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_ID);
        }
        if (articleType.getArticleTypeName() == null || ConstansUtil.EMPTY_STR.equals(articleType.getArticleTypeName())) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_RESULT);
        }
        if (articleType.getPriority() == null || articleType.getPriority() == 0) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_ID);
        }
        //添加创建时间
        articleType.setArticleTypeCreateTime(new Date());
        //调用dao层
        try {
            int result = articleTypeDao.insertArticleType(articleType);
            if (result != 0) {
                return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS);
            } else {
                return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
        }
    }

    @Override
    public ArticleTypeExecution delArticleType(List<Byte> list) {
        if (list == null || list.size() < 1) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.EMPTY_ID);
        }
        //调用dao层
        int result = articleTypeDao.deleteArticleType(list);
        if (result != 0) {
            return new ArticleTypeExecution(ArticleTypeStateEnum.SUCCESS);
        } else {
            return new ArticleTypeExecution(ArticleTypeStateEnum.INNER_ERROR);
        }
    }


}
