package com.cdq.Service;

import com.cdq.execution.AttentionExecution;
import com.cdq.model.Attention;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/8 15:02
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public interface AttentionService {

    /**
     * 根据用户id获取该用户所有关注用户
     *
     * @param attention
     * @return
     */
    AttentionExecution getAttentionListByUser(Attention attention, int indexPage, int pageSize);

    /**
     * 添加关注记录
     *
     * @param attention
     * @return
     */
    AttentionExecution addAttention(Attention attention);

    /**
     * 删除关注记录
     *
     * @param attention
     * @return
     */
    AttentionExecution delAttention(Attention attention);

}
