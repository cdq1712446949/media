package com.cdq.dao;

import com.cdq.model.UserReport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/13 4:47
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public class TestDao extends BaseTest {

    @Autowired
    private UserReportDao userReportDao;

    @Test
    public void testQuery(){
        UserReport userReport = new UserReport();
        List<UserReport> list = userReportDao.selectReport(userReport,0,10);
        System.out.println(list.size());
    }

}
