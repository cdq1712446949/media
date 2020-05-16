package com.cdq.dao;

import com.cdq.model.PersonInfo;
import com.cdq.model.SecretMessage;
import com.cdq.model.SystemMessage;
import com.cdq.model.User;
import com.cdq.util.MessageNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 15:20
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Repository
public interface SecretMessageDao {

    /**
     * 查询当前用户未读消息列表
     *
     * @param secretMessage
     * @return 以发送发用户id为主键，携带未读消息数量返回
     */
    List<MessageNumber> querySecretMessage(SecretMessage secretMessage);

    /**
     * 查询当前用户和当前聊天对象的未读消息
     * @param secretMessage
     * @return 当前用户和当前聊天对象的未读消息列表
     */
    List<SecretMessage> querySMByFromUser(SecretMessage secretMessage);

    /**
     * 查询历史消息记录
     * @param secretMessage
     * @return
     */
    List<SecretMessage> queryHistoryMessage(SecretMessage secretMessage);

    /**
     * 批量修改未读消息的状态
     * @param list
     */
    void updateMessageIsSee(@Param("list") List<Integer> list,@Param("isSee")Byte isSee);

    /**
     * 添加私信记录
     * @param secretMessage
     * @return
     */
    int insertSecretMessage (SecretMessage secretMessage);

    Integer queryAllMessageNum(@Param("userId") String userId);

    PersonInfo queryUserSMStatus(User user);

    List<SystemMessage> querySystemMessage(User user);

    int updateSystemIsSee(@Param("list") List<Integer> list,@Param("isSee")Byte isSee);

}
