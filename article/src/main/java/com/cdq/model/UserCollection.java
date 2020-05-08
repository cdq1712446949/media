package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserCollection {

    public static final int BAN_STATUS=-1;
    public static final int NORMAL_STATUS=0;

    private Integer collectionId;
    //用户所关注的用户
    private User user;
    //用户所关注的文章
    private Article article;
    private Byte collectionStatus;
    private Date collectionCreateTime;


}