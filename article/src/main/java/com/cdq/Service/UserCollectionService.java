package com.cdq.Service;


import com.cdq.execution.UserCollectionExecution;
import com.cdq.model.UserCollection;

public interface UserCollectionService {

    /**
     * 用户收藏记录管理
     * @param userCollection
     * @return
     */
    UserCollectionExecution userCollectionManage(UserCollection userCollection);

}
