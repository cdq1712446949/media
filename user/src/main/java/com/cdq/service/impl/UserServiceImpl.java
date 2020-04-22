package com.cdq.service.impl;

import com.cdq.dao.UserDao;
import com.cdq.enums.UserStateEnum;
import com.cdq.execution.UserExecution;
import com.cdq.model.User;
import com.cdq.model.UserInfo;
import com.cdq.service.UserService;
import com.cdq.util.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 校验参数
     * 1.username
     * 2.password
     * 获取到user后检查userStatus属性值
     *
     * @param user
     * @return
     */
    @Override
    public UserExecution login(User user) {
        //校验参数
        if (user == null) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserName() == null || user.getUserName().equals("")) {
            return new UserExecution(UserStateEnum.EMPTY_USERNAME);
        }
        if (user.getPassWord() == null || user.getPassWord().equals("")) {
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
     * @param user
     * @return
     */
    @Override
    public UserExecution getUerInfo(User user) {
        //校验参数
        if (user.getUserId() == null) {
            return new UserExecution(UserStateEnum.EMPTY_USER);
        }
        if (user.getUserId().equals("")) {
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
                return new UserExecution(UserStateEnum.SUCCESS,admin);
            } else {
                return new UserExecution(UserStateEnum.ACCOUNT_BAN);
            }
        } else {
            return new UserExecution(UserStateEnum.ACCOUNT_ERROR);
        }
    }


}
