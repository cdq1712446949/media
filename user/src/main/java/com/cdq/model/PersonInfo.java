package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/9 16:01
 * @description：
 * @modified By：
 * @version: 1.0.1
 */

@Getter
@Setter
@ToString
public class PersonInfo {

    public static byte SECRET_NO = -1;
    public static byte SECRET_YES = 0;


    Integer personInfoId;
    String userId;
    Byte secretStatus;

    User user;

}
