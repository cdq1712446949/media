package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
public class ArticleType implements Serializable {

    @Getter
    @Setter
    private Short articleTypeId;
    @Getter
    @Setter
    private String articleTypeName;
    @Getter
    @Setter
    private Byte priority;
    @Getter
    @Setter
    private Integer clickNum;
    @Getter
    @Setter
    private Date articleTypeCreateTime;
    @Getter
    @Setter
    private Date articleTypeLastEditTime;
    //父类文章类型
    @Getter
    @Setter
    private ArticleType parentArticleType;

}