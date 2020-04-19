package com.cdq.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Photo {
    @Getter
    @Setter
    private Integer photoId;
    @Getter
    @Setter
    private PhotoType photoTypeId;
    @Getter
    @Setter
    private Article article;
    @Getter
    @Setter
    private UserReport userReport;
    @Getter
    @Setter
    //表示图片上传顺序
    private short photoNum;
    @Getter
    @Setter
    private String photoName;
    @Getter
    @Setter
    private String photoAddr;
    @Getter
    @Setter
    private Date photoCreateTime;
    @Getter
    @Setter
    private Byte photoStatus;
    @Getter
    @Setter
    private Advertisement advertisement;
}