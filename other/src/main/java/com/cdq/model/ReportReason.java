package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReportReason {
    private Byte reportReasonId;

    private ReportReason parentReason;

    private String reportReasonName;

    private Date reportReasonCreateTime;

    private Date reportReasonLastEditTime;

}