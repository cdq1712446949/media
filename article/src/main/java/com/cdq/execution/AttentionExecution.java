package com.cdq.execution;

import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Attention;
import com.cdq.model.UserCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/8 15:03
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Getter
@Setter
@ToString
public class AttentionExecution {


    //状态值
    private int state;
    //状态信息
    private String stateInfo;
    //操作对象数量
    private int count;
    //单个操作对象
    private Attention attention;
    //操作对象列表
    private List<Attention> attentionList;

    //失败时使用的构造器
    public AttentionExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    //操作成功时使用的构造器
    public AttentionExecution(BaseStateEnum baseStateEnum,Attention attention) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.attention = attention;
    }

    //操作成功时使用的构造器
    public AttentionExecution(BaseStateEnum baseStateEnum, List<Attention> attentionList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.attentionList = attentionList;
    }



}
