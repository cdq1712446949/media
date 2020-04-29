package com.cdq.util;

import com.cdq.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 16:05
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Getter
@Setter
@ToString
public class MessageNumber {

    private User fromUser;
    private Integer messageNum;

}
