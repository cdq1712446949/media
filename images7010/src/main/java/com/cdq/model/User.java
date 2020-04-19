package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
public class User {

    public final static int SUPER_ADMIN = 0;//超级管理员
    public final static int ADMIN = 1;//普通管理员
    public final static int USER = 2;//用户
    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String passWord;
    @Getter
    @Setter
    private String nickName;
    @Getter
    @Setter
    private String userSex;
    @Getter
    @Setter
    private Byte userRole;
    @Getter
    @Setter
    private Integer userExprience;
    @Getter
    @Setter
    private Date userBirthday;
    @Getter
    @Setter
    private String userEmail;
    @Getter
    @Setter
    private String userHeadPhoto;
    @Getter
    @Setter
    private Byte userStatus;
    @Getter
    @Setter
    private Date userCreateTime;
    @Getter
    @Setter
    private Date userLastEditTime;
    @Getter
    @Setter
    private Date userLastLoginTime;
    @Getter
    @Setter
    private String userLastLoginIp;

    //用户简介
    @Getter
    @Setter
    private String userDesc;
    //用户手机号
    @Getter
    @Setter
    private String userPhone;
    //用户博客数量
    @Getter
    @Setter
    private int blogNum;
    //用户粉丝数量
    @Getter
    @Setter
    private int funsNum;
    //用户被浏览数量
    @Getter
    @Setter
    private int lookNum;
    //用户文章评论数量
    @Getter
    @Setter
    private int commentNum;


}