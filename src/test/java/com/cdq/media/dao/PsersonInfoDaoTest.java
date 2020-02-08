package com.cdq.media.dao;

import com.cdq.media.BaseTest;
import com.cdq.media.mapper.PersonInfoDao;
import com.cdq.media.model.PersonInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/17 11:26
 * @description：personInfo dao层测试
 * @modified By：
 * @version: 1.0.1
 */
public class PsersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void registerTest(){
        PersonInfo p=new PersonInfo();
        p.setUserId("18271579008757346");
        boolean result = personInfoDao.register(p);
    }

}
