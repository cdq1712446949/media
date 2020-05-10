package com.cdq.Service.impl;

import com.cdq.Service.UserCollectionService;
import com.cdq.dao.UserCollectionDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.AttentionExecution;
import com.cdq.execution.UserCollectionExecution;
import com.cdq.model.UserCollection;
import com.cdq.until.ConstansUtil;
import com.cdq.until.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        if (userCollection.getCollectionStatus() != UserCollection.BAN_STATUS &&
                userCollection.getCollectionStatus() != UserCollection.NORMAL_STATUS) {
            return new UserCollectionExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        //根据userId和articleId查询该记录是否存在
        UserCollection temp = userCollectionDao.duplicateCheck(userCollection);
        if (temp != null) {
            return new UserCollectionExecution(BaseStateEnum.BAD_REQUEST);
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

    /**
     * 查询收藏列表
     *
     * @param userCollection
     * @param indexPage
     * @param pageSize
     * @return
     */
    @Override
    public UserCollectionExecution getCollectionList(UserCollection userCollection, int indexPage, int pageSize) {
        //参数校验
        if (userCollection.getUser() == null) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (userCollection.getUser().getUserId() == null || ConstansUtil.EMPTY_STR.equals(userCollection.getUser().getUserId())) {
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //调用dao层
        List<UserCollection> result = userCollectionDao.selectUserCollection(userCollection,
                PageUtil.pageToRowIndex(indexPage, pageSize), pageSize);
        if (result == null) {
            UserCollectionExecution userCollectionExecution = new UserCollectionExecution(BaseStateEnum.INNER_ERROR);
            userCollectionExecution.setCount(userCollectionDao.querCount(userCollection));
            return userCollectionExecution;
        } else {
            return new UserCollectionExecution(BaseStateEnum.SUCCESS, result);
        }
    }

    /**
     * 删除收藏记录
     * @param userCollection
     * @return
     */
    @Override
    public UserCollectionExecution delCollection(UserCollection userCollection) {
        //参数校验
        if (userCollection.getCollectionId()==null||userCollection.getCollectionId()==0){
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        if (userCollection.getUser()==null || ConstansUtil.EMPTY_STR.equals(userCollection.getUser().getUserId())){
            return new UserCollectionExecution(BaseStateEnum.EMPTY_INFO);
        }
        //调用dao层删除关注记录
        int result = userCollectionDao.delCollection(userCollection);
        if (result!=0){
            UserCollectionExecution userCollectionExecution = new UserCollectionExecution(BaseStateEnum.SUCCESS);
            return userCollectionExecution;
        }else{
            return new UserCollectionExecution(BaseStateEnum.INNER_ERROR);
        }
    }


}
