package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
public class UserComment {

    public static final int  BAN_STATUS=-1;
    public static final int NORMAL_STATUS=0;

    @Getter
    @Setter
    private Integer userCommentId;
    //评论所属文章
    @Getter
    @Setter
    private Article article;
    //评论所属评论
    @Getter
    @Setter
    private UserComment userComment;
    //发表评论的用户
    @Getter
    @Setter
    private User fromUser;
    //被回复用户
    @Getter
    @Setter
    private User toUser;
    //该评论下的回复评论
    @Getter
    @Setter
    private List<UserComment> userCommentList;
    @Getter
    @Setter
    private Byte userCommentStatus;
    @Getter
    @Setter
    private Date userCommentCreateTime;
    @Getter
    @Setter
    private String userCommentContent;

}