package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Attention {
    private Integer attentionId;

    private User attentionUser;

    private User attentedUser;

    private Date attentionCreateTime;

    private short attentionStatus;


}