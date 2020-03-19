package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PersonInfo {
    @Getter
    @Setter
    private Integer personInfoId;
    @Getter
    @Setter
    private String userId;
    //表示用户是否接收私信
    @Getter
    @Setter
    private Byte secretStatus;


}