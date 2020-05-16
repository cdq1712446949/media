package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserReport {
    private Integer userReportId;
    //举报人
    private User reportUser;
    //被举报人
    private User reprotedUser;
    //被举报文章
    private Article article;
    //被举报评论
    private UserComment userComment;
    //举报原因
    private ReportReason reportReason;
    //举报原因描述
    private String userReportContent;

    private Byte userReportStatus;

    private Date userReportCreateTime;

    private List<String> photoList;

}