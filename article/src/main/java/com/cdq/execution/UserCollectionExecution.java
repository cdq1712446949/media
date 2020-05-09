package com.cdq.execution;


import com.cdq.enums.BaseStateEnum;
import com.cdq.model.UserCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@ToString
public class UserCollectionExecution {


    //状态值
    private int state;
    //状态信息
    private String stateInfo;
    //操作对象数量
    private int count;
    //单个操作对象
    private UserCollection userCollection;
    //操作对象列表
    private List<UserCollection> userCollectionList;

    //失败时使用的构造器
    public UserCollectionExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    //操作成功时使用的构造器
    public UserCollectionExecution(BaseStateEnum baseStateEnum,UserCollection userCollection) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.userCollection = userCollection;
    }

    //操作成功时使用的构造器
    public UserCollectionExecution(BaseStateEnum baseStateEnum,List<UserCollection> userCollectionList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.userCollectionList = userCollectionList;
    }


}
