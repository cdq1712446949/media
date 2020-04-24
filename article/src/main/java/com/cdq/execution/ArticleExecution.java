package com.cdq.execution;


import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author CDQ
 * created  on 2019/6.25
 * service调用对象，用于对controller返回数据
 */
public class ArticleExecution {

    //状态值
    @Getter
    @Setter
    private int state;
    //状态信息
    @Getter
    @Setter
    private String stateInfo;
    //操作对象数量
    @Getter
    @Setter
    private int count;
    //单个操作对象
    @Getter
    @Setter
    private Article article;
    //操作对象列表
    @Getter
    @Setter
    private List<Article> articleList;

    //失败时使用的构造器
    public ArticleExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    //操作成功时使用的构造器
    public ArticleExecution(BaseStateEnum baseStateEnum, Article article) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.article = article;
    }

    //操作成功时使用的构造器
    public ArticleExecution(BaseStateEnum baseStateEnum,List<Article> articles) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.articleList = articles;
    }

}
