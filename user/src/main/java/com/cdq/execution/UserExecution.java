package com.cdq.execution;


import com.cdq.enums.UserStateEnum;
import com.cdq.model.User;
import com.cdq.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserExecution {

    @Getter
    @Setter
    private int state;
    @Getter
    @Setter
    private String stateInfo;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private List<User> userList;
    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    private UserInfo userInfo;




    public UserExecution(UserStateEnum userStateEnum, UserInfo userInfo){
        this.state=userStateEnum.getState();
        this.stateInfo=userStateEnum.getStateInfo();
        this.userInfo=userInfo;
    }



    public UserExecution(UserStateEnum userStateEnum){
        this.state=userStateEnum.getState();
        this.stateInfo=userStateEnum.getStateInfo();
    }

    public UserExecution(UserStateEnum userStateEnum,User user){
        this.state=userStateEnum.getState();
        this.stateInfo=userStateEnum.getStateInfo();
        this.user=user;
    }

    public UserExecution(UserStateEnum userStateEnum,List<User> userList){
        this.state=userStateEnum.getState();
        this.stateInfo=userStateEnum.getStateInfo();
        this.userList=userList;
    }


}
