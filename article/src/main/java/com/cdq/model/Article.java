package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/17 13:15
 * @description：文章model
 * @modified By：
 * @version: 1.0.1
 */
@ToString
public class Article {
    @Getter
    @Setter
    private Integer articleId;
    @Getter
    @Setter
    private Short goodNum;
    @Getter
    @Setter
    private Short lookNum;
    @Getter
    @Setter
    private Byte articleStatus;
    @Getter
    @Setter
    private Date articleCreateTime;
    @Getter
    @Setter
    private Date articleLastEditTime;
    @Getter
    @Setter
    private String articleContent;
    /**
     *文章作者
     */
    @Getter
    @Setter
    private User user;
    /**
     * 文章类型
      */
    @Getter
    @Setter
    private ArticleType articleType;
    /**
     * 评论数量
     */
    @Getter
    @Setter
    private int commentNum;
    /**
     * 评论列表
     */
    @Getter
    @Setter
    private List<UserComment> userCommentList;

    @Getter
    @Setter
    private Date startTime;

    @Getter
    @Setter
    private Date endTime;

}
