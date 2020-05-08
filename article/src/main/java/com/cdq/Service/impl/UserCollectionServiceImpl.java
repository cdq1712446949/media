package com.cdq.Service.impl;

import com.cdq.Service.UserCollectionService;
import com.cdq.dao.UserCollectionDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.UserCollectionExecution;
import com.cdq.model.UserCollection;
import com.cdq.until.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户收藏记录管理service层
 *
 * @author cdq
 * created on 2019.9.16
 */
@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionDao userCollectionDao;

    /**
     * 用户收藏记录管理
     *
     * @param userCollection
     * @return
     */
    @Transactional
    @Override
    public UserCollectionExecution userCollectionManage(UserCollection userCollection) {
        //参数校验
        if (userCollection == null) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //userId不能为空
        if (userCollection.getUser() == null) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (userCollection.getUser().getUserId() == null || ConstansUtil.EMPTY_STR.equals(userCollection.getUser().getUserId())) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //articleId不能为空
        if (userCollection.getArticle() == null) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (userCollection.getArticle().getArticleId() == null || userCollection.getArticle().getArticleId() == 0) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //collectionStatus不能为空
        if (userCollection.getCollectionStatus() != UserCollection.BAN_STATUS && userCollection.getCollectionStatus() != UserCollection.NORMAL_STATUS) {
            return new UserCollectionExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //根据userId和articleId查询该记录是否存在
        UserCollection temp = userCollectionDao.duplicateCheck(userCollection);
        if (temp != null) {
            try {
                int result = userCollectionDao.updateUserCollection(userCollection);
                if (result == 1) {
                    return new UserCollectionExecution(BaseStateEnum.SUCCESS);
                } else {
                    return new UserCollectionExecution(BaseStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("修改收藏记录失败：" + e.getMessage() + new Date().toString());
            }
        } else {
            try {
                userCollection.setCollectionCreateTime(new Date());
                int result = userCollectionDao.insertUserCollection(userCollection);
                if (result == 1) {
                    return new UserCollectionExecution(BaseStateEnum.SUCCESS);
                } else {
                    return new UserCollectionExecution(BaseStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("添加收藏记录失败：" + e.getMessage() + new Date().toString());
            }
        }
    }


}
