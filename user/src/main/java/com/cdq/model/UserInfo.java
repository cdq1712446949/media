package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserInfo {

    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private Integer articleNum;
    @Getter
    @Setter
    private Integer funsNum;


}
