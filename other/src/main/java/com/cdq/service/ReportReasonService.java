package com.cdq.service;

import com.cdq.dto.ReportReasonExecution;
import com.cdq.model.ReportReason;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/12 0:57
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public interface ReportReasonService {

    ReportReasonExecution selectParentList();

    ReportReasonExecution selectChildList(ReportReason reportReason);

    ReportReasonExecution selectAllReason();

    ReportReasonExecution addReason(ReportReason reportReason);

    ReportReasonExecution delReason(ReportReason reportReason);

}
