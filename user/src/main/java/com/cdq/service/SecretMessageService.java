package com.cdq.service;

import com.cdq.execution.SecretMessageExecution;
import com.cdq.model.SecretMessage;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 21:20
 * @description：
 * @modified By：
 * @version: $
 */
public interface SecretMessageService {

    /**
     * 查询消息界面未读消息数量
     * @param secretMessage
     * @return
     */
    SecretMessageExecution getSecreMessageNum(SecretMessage secretMessage);

    /**
     * 获取当前用户和当前聊天对象的未读消息记录
     * @param secretMessage
     * @return
     */
    SecretMessageExecution getSMByFromUser(SecretMessage secretMessage);

    /**
     * 获取历史消息记录
     * @param secretMessage
     * @return
     */
    SecretMessageExecution getHistoryMessage(SecretMessage secretMessage);

    /**
     * 批量修改私信记录的状态
     * @param list
     * @param state
     * @return
     */
    SecretMessageExecution changeMessageState(List<Integer> list,Byte state);

    /**
     * 批量修改系统通知记录的状态
     * @param list
     * @param state
     * @return
     */
    SecretMessageExecution changeSystemState(List<Integer> list,Byte state);


    /**
     * 添加私信记录
     * @param secretMessage
     * @return
     */
    SecretMessageExecution addMessage(SecretMessage secretMessage);

    SecretMessageExecution getAllMessageNum(String userId);

}
