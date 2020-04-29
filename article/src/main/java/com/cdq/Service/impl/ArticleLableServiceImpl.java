package com.cdq.Service.impl;

import com.cdq.Service.ArticleLableService;
import com.cdq.dao.ArticleLableDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.ArticleLableExecution;
import com.cdq.model.ArticleLable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 8:57
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class ArticleLableServiceImpl implements ArticleLableService {

    @Autowired
    private ArticleLableDao articleLableDao;

    /**
     * 根据创建时间获取前十个话题
     * TODO 参数校验
     * @return
     */
    @Override
    public ArticleLableExecution getArtiLable() {
        List<ArticleLable> result = articleLableDao.queryArtiLable();
        if (result==null){
            return new ArticleLableExecution(BaseStateEnum.INNER_ERROR);
        }else{
            return new ArticleLableExecution(BaseStateEnum.SUCCESS,result);
        }
    }
}
