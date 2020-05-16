package com.cdq.execution;


import com.cdq.enums.SecretMessageEnum;
import com.cdq.model.SecretMessage;
import com.cdq.model.SystemMessage;
import com.cdq.util.MessageNumber;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class SecretMessageExecution {

    @Getter
    @Setter
    private int state;
    @Getter
    @Setter
    private String stateInfo;
    @Getter
    @Setter
    private SecretMessage secretMessage;
    @Getter
    @Setter
    private List<SecretMessage> secretMessageList;
    @Getter
    @Setter
    private List<SystemMessage> systemMessageList;
    @Getter
    @Setter
    private List<MessageNumber> messageNumberList;
    @Getter
    @Setter
    private int count;


    public SecretMessageExecution(SecretMessageEnum secretMessageEnum) {
        this.state = secretMessageEnum.getState();
        this.stateInfo = secretMessageEnum.getStateInfo();
    }

    public SecretMessageExecution(SecretMessageEnum secretMessageEnum, SecretMessage secretMessage) {
        this.state = secretMessageEnum.getState();
        this.stateInfo = secretMessageEnum.getStateInfo();
        this.secretMessage = secretMessage;
    }

    public SecretMessageExecution(SecretMessageEnum secretMessageEnum, ArrayList<MessageNumber> messageNumberList) {
        this.state = secretMessageEnum.getState();
        this.stateInfo = secretMessageEnum.getStateInfo();
        this.messageNumberList = messageNumberList;
    }

    public SecretMessageExecution(SecretMessageEnum secretMessageEnum, List<SecretMessage> secretMessageList) {
        this.state = secretMessageEnum.getState();
        this.stateInfo = secretMessageEnum.getStateInfo();
        this.secretMessageList = secretMessageList;
    }


}
