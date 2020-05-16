package com.cdq.service.impl;

import com.cdq.dao.UserReportDao;
import com.cdq.dto.UserReportExecution;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.ReportResult;
import com.cdq.model.User;
import com.cdq.model.UserReport;
import com.cdq.service.UserReportService;
import com.cdq.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
     *
     * @param userReport
     * @return
     */
    @Override
    public UserReportExecution addUserReport(UserReport userReport) {
        userReport.setUserReportCreateTime(new Date());
        if (userReport.getArticle() == null && userReport.getUserComment() == null) {
            return new UserReportExecution(BaseStateEnum.ILLEGAL_PARAMETER);
        }
        int result = userReportDao.insertUserReport(userReport);
        if (result != 0) {
            return new UserReportExecution(BaseStateEnum.SUCCESS);
        } else {
            return new UserReportExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    @Override
    public UserReportExecution getUserReport(UserReport userReport, int indexPage, int pageSize) {
        try {
            List<UserReport> list = userReportDao.selectReport(userReport, PageUtil.pageToRowIndex(indexPage, pageSize), pageSize);
            int count = userReportDao.selectReportCount(userReport);
            if (list != null ) {
                UserReportExecution result = new UserReportExecution(BaseStateEnum.SUCCESS, list);
                result.setCount(count);
                return result;
            } else {
                return new UserReportExecution(BaseStateEnum.INNER_ERROR);

            }
        } catch (Exception e) {
            UserReportExecution userReportExecution =  new UserReportExecution(BaseStateEnum.INNER_ERROR);
            userReportExecution.setStateInfo(e.getMessage());
            return userReportExecution;
        }
    }

    @Override
    @Transactional
    public UserReportExecution addResult(ReportResult reportResult) {
        try {
            int result = userReportDao.insertResult(reportResult);
            //修改举报记录
            UserReport userReport = new UserReport();
            userReport.setUserReportId(reportResult.getUserReport().getUserReportId());
            userReport.setUserReportStatus((byte) 1);
            int result1 = userReportDao.updateReportStatus(userReport);
            if (result!=0){
                return new UserReportExecution(BaseStateEnum.SUCCESS);
            }else{
                return new UserReportExecution(BaseStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            return new UserReportExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    @Override
    @Transactional
    public UserReportExecution changeUserStatus(User user) {
        try {
            int result = userReportDao.updateUserStatus(user);
            if (result!=0){
                return new UserReportExecution(BaseStateEnum.SUCCESS);
            }else{
                return new UserReportExecution(BaseStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            return new UserReportExecution(BaseStateEnum.INNER_ERROR);
        }
    }

}
