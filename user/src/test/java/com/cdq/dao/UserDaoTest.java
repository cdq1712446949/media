package com.cdq.dao;

import com.cdq.model.SecretMessage;
import com.cdq.model.User;
import com.cdq.util.MessageNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private SecretMessageDao secretMessageDao;

    @Test
    public void testLogin(){
        User user = new User();
        user.setUserName("root");
        user.setPassWord("980814");
        User u1 = userDao.queryUserByUserName(user);
        System.out.println(u1.getUserId());
    }

    @Test
    public void testQuery(){
        User user = new User();
        user.setUserId("19980818");
        User user1=new User();
        user1.setUserId("18271579008757346");
        SecretMessage sm = new SecretMessage();
        sm.setToUser(user);
        sm.setFromUser(user1);
        List<SecretMessage> list = secretMessageDao.querySMByFromUser(sm);
        System.out.println(list.size());
    }

    @Test
    public void testQueryHis(){
        User user = new User();
        user.setUserId("19980818");
        User user1=new User();
        user1.setUserId("18271579008757346");
        SecretMessage sm = new SecretMessage();
        sm.setToUser(user);
        sm.setFromUser(user1);
        List<SecretMessage> list = secretMessageDao.queryHistoryMessage(sm);
        System.out.println(list.size());
    }

    @Test
    public void testUpdate(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        secretMessageDao.updateMessageIsSee(list, (byte) 1);

    }

}
