package com.cdq.service.impl;

import com.cdq.dao.UserReportDao;
import com.cdq.dto.UserReportExecution;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.UserReport;
import com.cdq.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 23:48
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class UserReportServiceImpl implements UserReportService {

    @Autowired
    private UserReportDao userReportDao;

    /**
     * 添加举报记录
     * @param userReport
     * @return
     */
    @Override
    public UserReportExecution addUserReport(UserReport userReport) {
        userReport.setUserReportCreateTime(new Date());
        if (userReport.getArticle()==null&&userReport.getUserComment()==null){
            return new UserReportExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        int result = userReportDao.insertUserReport(userReport);
        if (result!=0){
            return new UserReportExecution(BaseStateEnum.SUCCESS);
        }else{
            return new UserReportExecution(BaseStateEnum.INNER_ERROR);
        }
    }
}
