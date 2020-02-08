package com.cdq.media.service.impl;

import com.cdq.media.enums.UserStateEnum;
import com.cdq.media.execution.UserExecution;
import com.cdq.media.mapper.UserDao;
import com.cdq.media.model.ThumbsCollection;
import com.cdq.media.model.User;
import com.cdq.media.model.UserInfo;
import com.cdq.media.service.UserService;
import com.cdq.media.unit.EncryptionUtil;
import com.cdq.media.unit.UserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 校验参数
     * 1.username
     * 2.password
     * 获取到user后检查userStatus属性值
     * @param user
     * @return
     */
    @Override
    public UserExecution login(User user) {
        //校验参数
        if(user==null){
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserName()==null||user.getUserName().equals("")){
            return new UserExecution(UserStateEnum.EMPTY_USERNAME);
        }
        if (user.getPassWord()==null||user.getPassWord().equals("")){
            return new UserExecution(UserStateEnum.EMPTY_PASSWORD);
        }
        //密码转换为密文
        user.setPassWord(EncryptionUtil.getEncryptionStr(user.getPassWord()));
        //请求数据库获取数据
        User result=userDao.queryUserByUserName(user);
        if (result==null){
            return new UserExecution(UserStateEnum.ACCOUNT_ERROR);
        }else {
            if (result.getUserStatus()==0){
                return new UserExecution(UserStateEnum.SUCCESS,result);
            }else {
                return new UserExecution(UserStateEnum.ACCOUNT_BAN);
            }
        }
    }

    /**
     * 用户注册
     * 根据用户传递的信息添加数据库user表记录
     * user表数据添加完成后，为用户注册person_info表数据记录
     * @param user
     * @return
     */
    //TODO 编写方法体
    @Override
    @Transactional
    public UserExecution register(User user) {
        //校验参数
        if (user != null){
            //添加userId参数
            user.setUserId(UserIdUtil.createUserId());
            //添加用户创建时间
            user.setUserCreateTime(new Date());
            if (user.getUserName() == null || user.getUserName().equals("")){
                return new UserExecution(UserStateEnum.EMPTY_USERNAME);
            }
            if (user.getPassWord() == null || user.getPassWord().equals("")){
                return new UserExecution(UserStateEnum.EMPTY_ARGS);
            }
            //用户昵称左右不能添加空格，不允许昵称都是空格
            if (user.getNickName() == null || user.getNickName().trim().equals("")){
                return new UserExecution(UserStateEnum.EMPTY_ARGS);
            }
            //检查用户昵称中是否包含特殊字符
            if (UserIdUtil.isSpecialChar(user.getNickName())){
                return new UserExecution(UserStateEnum.EMPTY_NICKNAME);
            }
            //校验用户性别参数
            if (!UserIdUtil.checkSex(user.getUserSex())){
                return new UserExecution(UserStateEnum.EMPTY_USER);
            }
            if (user.getUserBirthday() == null || user.getUserBirthday().equals("")){
                return new UserExecution(UserStateEnum.EMPTY_ARGS);
            }
            //添加数据库记录
            try {
                boolean result = userDao.registerUser(user);
                if (result){
                    return new UserExecution(UserStateEnum.SUCCESS);
                }else{
                    return new UserExecution(UserStateEnum.INNER_ERROR);
                }
            }catch (Exception e){
                throw new RuntimeException("添加用户记录失败:"+e.getMessage());
            }
        }else {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
    }

    /**
     * 获取用户信息
     * 1.博客数量
     * 2.粉丝数量
     * 3.访问数量
     * 4.评论数量
     * @param user
     * @return
     */
    @Override
    public UserExecution getUerInfo(User user) {
        //校验参数
        if (user.getUserId()==null){
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserId().equals("")){
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        //请求数据库获取数据
        UserInfo userInfo=userDao.queryUserInfo(user);
        return new UserExecution(UserStateEnum.SUCCESS,userInfo);
    }

    /**
     * 获取登录用户对当前文章的点赞和收藏状态
     * @param thumbsCollection
     * @return
     */
    @Override
    public UserExecution getThumbsCollection(ThumbsCollection thumbsCollection) {
        //校验参数
        //userId不能是0
        if (thumbsCollection.getUserId()==0){
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        //articleId不能是0
        if (thumbsCollection.getArticleId()==0){
            return new UserExecution(UserStateEnum.EMPTY_ARTICLE);
        }
        ThumbsCollection thumbsCollection1=userDao.queryThumbsCollection(thumbsCollection);
        if (thumbsCollection1!=null){
            if (thumbsCollection1.getCollectionId()==0){
                thumbsCollection1.setIsCollection(-1);
            }
            if (thumbsCollection1.getThumbsUpId()==0){
                thumbsCollection1.setIsThumbsUp(-1);
            }
        }
        return new UserExecution(UserStateEnum.SUCCESS,thumbsCollection1);
    }
}
