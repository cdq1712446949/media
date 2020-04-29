package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

public class ThumbsUp {

    @Getter
    @Setter
    private int thumbsUpId;
    //点赞用户
    @Getter
    @Setter
    private User user;
    //被点赞的文章
    @Getter
    @Setter
    private Article article;
    @Getter
    @Setter
    private Date upCreateTime;
    @Setter
    @Getter
    private Byte upStatus;

}
