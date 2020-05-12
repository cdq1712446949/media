package com.cdq.service;

import com.cdq.dto.UserReportExecution;
import com.cdq.model.UserReport;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 23:48
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public interface UserReportService {

    /**
     * 添加举报记录
     * @param userReport
     * @return
     */
    UserReportExecution addUserReport(UserReport userReport);

}
