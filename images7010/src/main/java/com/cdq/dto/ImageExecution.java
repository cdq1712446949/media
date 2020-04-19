package com.cdq.dto;


import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Article;
import com.cdq.model.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author CDQ
 * created  on 2019/6.25
 * service调用对象，用于对controller返回数据
 */
public class ImageExecution {

    //状态值
    @Getter
    @Setter
    private int state;
    //状态信息
    @Getter
    @Setter
    private String stateInfo;
    //操作对象数量
    @Getter
    @Setter
    private int count;
    //单个操作对象
    @Getter
    @Setter
    private Photo photo;
    //操作对象列表
    @Getter
    @Setter
    private List<Photo> photoList;

    //失败时使用的构造器
    public ImageExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    //操作成功时使用的构造器
    public ImageExecution(BaseStateEnum baseStateEnum, Photo photo) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.photo = photo;
    }

    //操作成功时使用的构造器
    public ImageExecution(BaseStateEnum baseStateEnum, List<Photo> photoList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.photoList = photoList;
    }




}
