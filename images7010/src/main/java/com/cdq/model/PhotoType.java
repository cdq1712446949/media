package com.cdq.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PhotoType {

    /**
     * 表示图片类型
     */
    public static int ADVERTISEMENT_TYPE=1;
    public static int REPORT_TYPE=1;
    public static int ARTICLE_TYPE=1;

    @Getter
    @Setter
    private Byte photoTypeId;
    @Getter
    @Setter
    private String photoTypeDesc;
    @Getter
    @Setter
    private Date photoTypeCreateTime;
    @Getter
    @Setter
    private String photoTypeName;
    @Getter
    @Setter
    private Byte photoTypeStatus;


}