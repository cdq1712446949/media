package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ThumbsCollection {

//    public static final int TRUE=1; //是
//    public static final int FALSE=0; //否

    //用户id
    @Getter
    @Setter
    private int userId;
    //文章id
    @Getter
    @Setter
    private int articleId;
    //点赞记录id
    @Getter
    @Setter
    private int thumbsUpId;
    //收藏记录id
    @Getter
    @Setter
    private int collectionId;
    //是否点赞了该文章
    @Getter
    @Setter
    private int isThumbsUp;
    //是否收藏了该文章
    @Getter
    @Setter
    private int isCollection;

}
