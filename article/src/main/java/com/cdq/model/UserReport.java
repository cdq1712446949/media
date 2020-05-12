package com.cdq.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserReport {
    @Getter
    @Setter
    private Integer userReportId;
    //举报人
    @Getter
    @Setter
    private User reportUser;
    //被举报人
    @Getter
    @Setter
    private User reprotedUser;
    //被举报文章
    @Getter
    @Setter
    private Article article;
    //被举报评论
    @Getter
    @Setter
    private UserComment userComment;
    //举报原因
    @Getter
    @Setter
    private ReportReason reportReason;
    //举报原因描述
    @Getter
    @Setter
    private String userReportContent;
    @Getter
    @Setter
    private Byte userReportStatus;
    @Getter
    @Setter
    private Date userReportCreateTime;



}