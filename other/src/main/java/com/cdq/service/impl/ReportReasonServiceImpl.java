package com.cdq.service.impl;

import com.cdq.dao.ReportReasonDao;
import com.cdq.dao.ReportResultDao;
import com.cdq.dto.ReportReasonExecution;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.ReportReason;
import com.cdq.service.ReportReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/12 0:58
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class ReportReasonServiceImpl implements ReportReasonService {

    @Autowired
    private ReportReasonDao reportReasonDao;

    @Override
    public ReportReasonExecution selectParentList() {
        List<ReportReason> result = reportReasonDao.queryParentList();
        if (result!=null){
            return new ReportReasonExecution(BaseStateEnum.SUCCESS,result);
        }else{
            return new ReportReasonExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    @Override
    public ReportReasonExecution selectChildList(ReportReason reportReason) {
        //TODO 看情况添加类型校验：验证参数类型id是否是一级类型id
        if (reportReason.getParentReason()==null&&reportReason.getParentReason().getReportReasonId()==null){
            return new ReportReasonExecution(BaseStateEnum.EMPTY_INFO);
        }
        List<ReportReason> list = reportReasonDao.queryChildList(reportReason);
        if (list!=null){
            return new ReportReasonExecution(BaseStateEnum.SUCCESS,list);
        }else{
            return new ReportReasonExecution(BaseStateEnum.INNER_ERROR);
        }
    }
}
