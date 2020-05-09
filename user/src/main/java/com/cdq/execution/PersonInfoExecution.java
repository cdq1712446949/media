package com.cdq.execution;


import com.cdq.enums.BaseStateEnum;
import com.cdq.enums.SecretMessageEnum;
import com.cdq.model.PersonInfo;
import com.cdq.model.SecretMessage;
import com.cdq.util.MessageNumber;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoExecution {

    @Getter
    @Setter
    private int state;
    @Getter
    @Setter
    private String stateInfo;
    @Getter
    @Setter
    private PersonInfo personInfo;
    @Getter
    @Setter
    private List<PersonInfo> personInfoList;
    @Getter
    @Setter
    private int count;

    public PersonInfoExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    public PersonInfoExecution(BaseStateEnum baseStateEnum, PersonInfo personInfo) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.personInfo = personInfo;
    }


    public PersonInfoExecution(BaseStateEnum baseStateEnum, List<PersonInfo> personInfoList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.personInfoList = personInfoList;
    }


}
