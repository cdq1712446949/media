package com.cdq.dao;

import com.cdq.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/3 15:40
 * @description：UserDao测试类
 * @modified By：
 * @version: 1.0.1
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    private UserDao userDao;

    @Test
    public void testLogin(){
        User user = new User();
        user.setUserName("root");
        user.setPassWord("980814");
        User u1 = userDao.queryUserByUserName(user);
        System.out.println(u1.getUserId());
    }

}
