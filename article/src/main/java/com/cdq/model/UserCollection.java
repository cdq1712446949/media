package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserCollection {

    private Integer collectionId;
    //用户所关注的用户
    private User user;
    //用户所关注的文章
    private Article article;
    private Date collectionCreateTime;


}