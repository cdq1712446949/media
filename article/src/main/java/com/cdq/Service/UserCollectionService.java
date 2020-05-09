package com.cdq.Service;


import com.cdq.execution.UserCollectionExecution;
import com.cdq.model.UserCollection;

public interface UserCollectionService {

    /**
     * 用户收藏记录管理
     *
     * @param userCollection
     * @return
     */
    UserCollectionExecution userCollectionManage(UserCollection userCollection);

    /**
     * 查询收藏列表
     * @param userCollection
     * @param indexPage
     * @param pageSize
     * @return
     */
    UserCollectionExecution getCollectionList(UserCollection userCollection, int indexPage, int pageSize);

    /**
     * 删除收藏记录
     * @param userCollection
     * @return
     */
    UserCollectionExecution delCollection(UserCollection userCollection);

}
