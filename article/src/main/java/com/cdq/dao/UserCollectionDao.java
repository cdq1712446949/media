package com.cdq.dao;

import com.cdq.model.UserCollection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userCollcetionDao")
public interface UserCollectionDao {

    /**
     * 查询收藏记录
     * @param userCollection
     * @return
     */
    List<UserCollection> selectUserCollection(@Param("userCollection") UserCollection userCollection,
                                              @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 查重使用接口
     * @param userCollection
     * @return
     */
    UserCollection duplicateCheck(UserCollection userCollection);

    /**
     * 添加收藏记录
     * @param userCollection
     * @return
     */
    int insertUserCollection(UserCollection userCollection);

    /**
     * 修改收藏记录
     * @param userCollection
     * @return
     */
    int updateUserCollection(UserCollection userCollection);

    /**
     * 删除收藏记录
     * @param userCollection
     * @return
     */
    int delCollection(UserCollection userCollection);

    /**
     * 查询收藏列表数量
     * @param userCollection
     * @return
     */
    int querCount(UserCollection userCollection);

}
