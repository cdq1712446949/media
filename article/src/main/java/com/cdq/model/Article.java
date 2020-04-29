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
@Getter
@Setter
public class Article {

    private Integer articleId;
    private Short lookNum;
    private Integer goodNum;
    private String videoSrc;
    private Byte articleStatus;
    private Date articleCreateTime;
    private Date articleLastEditTime;
    private String articleContent;
    /**
     *文章作者
     */
    private User user;
    /**
     * 文章类型
      */
    private ArticleType articleType;
    private ArticleLable articleLable;
    /**
     * 评论数量
     */
    private Integer commentNum;
    /**
     * 评论列表
     */
    private List<UserComment> userCommentList;
    private Date startTime;
    private Date endTime;
    private List<Photo> photoList;

}
