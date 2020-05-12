package com.cdq.dao;

import com.cdq.model.Article;
import com.cdq.model.ReportReason;
import com.cdq.model.User;
import com.cdq.model.UserReport;
import lombok.ToString;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/12 2:48
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public class dao extends BaseTest {

    @Autowired
    private UserReportDao userReportDao;

    @Test
    public void test(){
        UserReport userReport = new UserReport();
        User reportUser = new User();
        reportUser.setUserId("19980818");
        userReport.setReportUser(reportUser);
        User reportedUser = new User();
        reportedUser.setUserId("12771588057251319");
        userReport.setReprotedUser(reportedUser);
        Article article = new Article();
        article.setArticleId(1);
        userReport.setArticle(article);
        ReportReason reportReason = new ReportReason();
        reportReason.setReportReasonId((byte) 1);
        userReport.setReportReason(reportReason);
        userReport.setUserReportCreateTime(new Date());
        userReport.setUserReportContent("ssssss");
        int result = userReportDao.insertUserReport(userReport);
        System.out.println(result);
    }

}
