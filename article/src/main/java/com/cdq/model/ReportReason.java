package com.cdq.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ReportReason {
    @Getter
    @Setter
    private Byte reportReasonId;
    @Getter
    @Setter
    private String reportReasonName;
    @Getter
    @Setter
    private Date reportReasonCreateTime;
    @Getter
    @Setter
    private Date reportReasonLastEditTime;


}