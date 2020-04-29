package com.cdq.service.impl;

import com.cdq.dao.SecretMessageDao;
import com.cdq.enums.SecretMessageEnum;
import com.cdq.execution.SecretMessageExecution;
import com.cdq.model.SecretMessage;
import com.cdq.service.SecretMessageService;
import com.cdq.util.MessageNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 21:24
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class SecretMessageServiceImpl implements SecretMessageService {

    @Autowired
    private SecretMessageDao secretMessageDao;

    /**
     * 获取未读消息列表
     * @param secretMessage
     * @return
     */
    @Override
    public SecretMessageExecution getSecreMessageNum(SecretMessage secretMessage) {
        //参数校验
        if (secretMessage.getToUser()==null){
            return new SecretMessageExecution(SecretMessageEnum.EMPTY_USER);
        }
        //调用service层
        List<MessageNumber> result = secretMessageDao.querySecretMessage(secretMessage);
        if (result != null) {
            return new SecretMessageExecution(SecretMessageEnum.SUCCESS, (ArrayList<MessageNumber>) result);
        } else {
            return new SecretMessageExecution(SecretMessageEnum.INNER_ERROR);
        }
    }

    /**
     * 获取当前聊天对象的未读消息
     * @param secretMessage
     * @return
     */
    @Override
    public SecretMessageExecution getSMByFromUser(SecretMessage secretMessage) {
        //参数校验
        if (secretMessage.getFromUser()==null||secretMessage.getToUser()==null){
            return new SecretMessageExecution(SecretMessageEnum.EMPTY_USER);
        }
        //调用service层
        List<SecretMessage> result = secretMessageDao.querySMByFromUser(secretMessage);
        if (result != null) {
            return new SecretMessageExecution(SecretMessageEnum.SUCCESS, result);
        } else {
            return new SecretMessageExecution(SecretMessageEnum.INNER_ERROR);
        }
    }

    /**
     * 获取历史消息记录
     * @param secretMessage
     * @return
     */
    @Override
    public SecretMessageExecution getHistoryMessage(SecretMessage secretMessage) {
        //参数校验
        if (secretMessage.getFromUser()==null||secretMessage.getToUser()==null){
            return new SecretMessageExecution(SecretMessageEnum.EMPTY_USER);
        }
        //调用service层
        List<SecretMessage> result = secretMessageDao.queryHistoryMessage(secretMessage);
        if (result != null) {
            return new SecretMessageExecution(SecretMessageEnum.SUCCESS, result);
        } else {
            return new SecretMessageExecution(SecretMessageEnum.INNER_ERROR);
        }
    }

    /**
     * 批量修改私信状态
     * @param list
     * @param state
     * @return
     */
    @Override
    public SecretMessageExecution changeMessageState(List<Integer> list, Byte state) {
        if (list==null||list.size()==0){
            return new SecretMessageExecution(SecretMessageEnum.SUCCESS);
        }
        secretMessageDao.updateMessageIsSee(list,state);
        return new SecretMessageExecution(SecretMessageEnum.SUCCESS);
    }
}
