package com.cdq.media.dao;

import com.cdq.media.BaseTest;
import com.cdq.media.mapper.UserDao;
import com.cdq.media.model.User;
import com.cdq.media.unit.UserIdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 14:57
 * @description：user Dao层测试类
 * @modified By：
 * @version: 1.0.1
 */
public class UserDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testRegister() {
       User user = new User();
       user.setUserId(UserIdUtil.createUserId());
       user.setPassWord("980184");
       user.setNickName("ヅてＤＱ");
       user.setUserBirthday(new Date());
       user.setUserCreateTime(new Date());
       user.setUserHeadPhoto("test");
       user.setUserName("root");
       user.setUserRole((byte) 0);
       user.setUserDesc("老子是超级管理员");
       user.setUserSex("男");
       boolean result = userDao.registerUser(user);
    }

}
