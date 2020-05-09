package com.cdq.service.impl;

import com.cdq.dao.PersonInfoDao;
import com.cdq.dao.UserDao;
import com.cdq.enums.UserStateEnum;
import com.cdq.execution.UserExecution;
import com.cdq.model.PersonInfo;
import com.cdq.model.User;
import com.cdq.model.UserInfo;
import com.cdq.service.UserService;
import com.cdq.util.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonInfoDao personInfoDao;

    /**
     * 校验参数
     * 1.username
     * 2.password
     * 获取到user后检查userStatus属性值
     *
     * @param user 参数
     * @return 结果
     */
    @Override
    public UserExecution login(User user) {
        //校验参数
        if (user == null) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserName() == null || user.getUserName().equals(ConstansUtil.EMPTY)) {
            return new UserExecution(UserStateEnum.EMPTY_USERNAME);
        }
        if (user.getPassWord() == null || user.getPassWord().equals(ConstansUtil.EMPTY)) {
            return new UserExecution(UserStateEnum.EMPTY_PASSWORD);
        }
        //密码转换为密文
//        user.setPassWord(EncryptionUtil.getEncryptionStr(user.getPassWord()));
        //请求数据库获取数据
        User result = userDao.queryUserByUserName(user);
        if (result == null) {
            return new UserExecution(UserStateEnum.ACCOUNT_ERROR);
        } else {
            if (result.getUserStatus() == 0) {
                return new UserExecution(UserStateEnum.SUCCESS, result);
            } else {
                return new UserExecution(UserStateEnum.ACCOUNT_BAN);
            }
        }
    }

    /**
     * 获取用户信息
     * 1.博客数量
     * 2.粉丝数量
     * 3.访问数量
     * 4.评论数量
     *
     * @param user 参数
     * @return 结果
     */
    @Override
    public UserExecution getUerInfo(User user) {
        //校验参数
        if (user.getUserId() == null) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserId().equals(ConstansUtil.EMPTY)) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        //请求数据库获取数据
        UserInfo userInfo = userDao.queryUserInfo(user);
        return new UserExecution(UserStateEnum.SUCCESS, userInfo);
    }

    @Override
    public UserExecution adminLogin(User user) {
        //参数校验
        if (user.getUserName() == null || ConstansUtil.EMPTY.equals(user.getUserName())) {
            return new UserExecution(UserStateEnum.EMPTY_USERNAME);
        }
        if (user.getPassWord() == null || ConstansUtil.EMPTY.equals(user.getPassWord())) {
            return new UserExecution(UserStateEnum.EMPTY_PASSWORD);
        }
        //调用dao层
        User admin = userDao.querySuperAdmin(user);
        if (admin != null) {
            if (!admin.getUserStatus().equals(User.STATUS_FROZEN)) {
                return new UserExecution(UserStateEnum.SUCCESS, admin);
            } else {
                return new UserExecution(UserStateEnum.ACCOUNT_BAN);
            }
        } else {
            return new UserExecution(UserStateEnum.ACCOUNT_ERROR);
        }
    }

    /**
     * 用户注册接口
     *
     * @param user 参数
     * @return 结果
     */
    @Override
    public UserExecution register(User user) {
        //TODO 参数校验
        user.setUserCreateTime(new Date());
        //处理头像
        if (user.getUserHeadPhoto() == null || ConstansUtil.EMPTY.equals(user.getUserHeadPhoto())) {
            user.setUserHeadPhoto("\\head\\default.png");
        }
        //调用dao层
        try {
            //注册user表
            int result = userDao.registerUser(user);
            //注册PersonInfo表
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(user.getUserId());
            int result1 = personInfoDao.insertPersonInfo(personInfo);
            if (result != 0 && result1 != 0) {
                return new UserExecution(UserStateEnum.SUCCESS);
            } else {
                return new UserExecution(UserStateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            return new UserExecution(UserStateEnum.ALREAD_EXIT);
        }
    }

    /**
     * 查询用户文章数量以及粉丝数量
     *
     * @param user 参数
     * @return 结果
     */
    @Override
    public UserExecution getUserInfo(User user) {
        //参数校验
        if (user == null || user.getUserId() == null || ConstansUtil.EMPTY.equals(user.getUserId())) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        //调用dao层
        UserInfo userInfo = userDao.queryArtFunNum(user);
        if (userInfo != null) {
            return new UserExecution(UserStateEnum.SUCCESS, userInfo);
        } else {
            return new UserExecution(UserStateEnum.INNER_ERROR);
        }
    }

    @Override
    public UserExecution changeUserInfo(User user) {
        //校验参数
        if (user.getUserId() == null || ConstansUtil.EMPTY.equals(user.getUserId())) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserSex() != null && !ConstansUtil.EMPTY.equals(user.getUserSex())) {
            if (!ConstansUtil.MAN.equals(user.getUserSex()) && !ConstansUtil.WOMAN.equals(user.getUserSex())) {
                return new UserExecution(UserStateEnum.ILLEGAL_PARAMETER);
            }
        }
        //调用dao层
        int result = userDao.updateUserInfo(user);
        if (result != 0) {
            return new UserExecution(UserStateEnum.SUCCESS);
        } else {
            return new UserExecution(UserStateEnum.INNER_ERROR);
        }
    }

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    @Override
    public UserExecution selectInfo(User user) {
        //校验参数
        if (user.getUserId()==null|ConstansUtil.EMPTY.equals(user.getUserId())){
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        //调用dao层
        User result = userDao.queryInfo(user);
        if (result!=null){
            return new UserExecution(UserStateEnum.SUCCESS,result);
        }else{
            return new UserExecution(UserStateEnum.INNER_ERROR);
        }
    }


}
