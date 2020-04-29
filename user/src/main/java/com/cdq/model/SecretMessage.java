package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SecretMessage {
    private Integer secretMessageId;

    private User fromUser;

    private User toUser;

    private Byte isSee;

    private Date messageCreateTime;

    private String messageContent;

    private Integer umMessageNum;

}