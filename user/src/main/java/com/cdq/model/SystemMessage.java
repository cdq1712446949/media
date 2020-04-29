package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SystemMessage {
    private Integer systemMessageId;

    private User toUser;

    private String messageContent;

    private Date messageCreateTime;

    private Byte isSee;


}