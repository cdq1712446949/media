package com.cdq.Service;


import com.cdq.execution.ThumbsUpExecution;
import com.cdq.model.ThumbsUp;

public interface ThumbsUpService {

    /**
     * 添加点赞记录
     * @param thumbsUp
     * @return
     */
    ThumbsUpExecution addThumbsUp(ThumbsUp thumbsUp);

    /**
     * 修改点赞记录
     * @param thumbsUp
     * @return
     */
    ThumbsUpExecution changeThumbsUp(ThumbsUp thumbsUp);

    /**
     * 点赞记录管理
     * @param thumbsUp
     * @return
     */
    ThumbsUpExecution thumbsManage(ThumbsUp thumbsUp);

}
