package com.cdq.dao;

import com.cdq.model.ArticleLable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 8:44
 * @description：话题dao层
 * @modified By：
 * @version: $
 */
@Repository
public interface ArticleLableDao {

    /**
     * 根据创建时间选择前十个话题
     * @return
     */
    List<ArticleLable> queryArtiLable();

}
