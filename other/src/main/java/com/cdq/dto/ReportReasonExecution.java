package com.cdq.dto;


import com.cdq.enums.BaseStateEnum;
import com.cdq.model.ReportReason;
import com.cdq.model.UserReport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class ReportReasonExecution implements Serializable {

    private int state;
    private String stateInfo;
    private ReportReason reportReason;
    private List<ReportReason> reportReasonList;
    private int count;

    public ReportReasonExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    public ReportReasonExecution(BaseStateEnum baseStateEnum, ReportReason reportReason) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.reportReason = reportReason;
    }

    public ReportReasonExecution(BaseStateEnum baseStateEnum, List<ReportReason> reportReasonList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.reportReasonList = reportReasonList;
    }


}
