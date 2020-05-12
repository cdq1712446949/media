package com.cdq.dto;


import com.cdq.enums.AdvertisementStateEnum;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Advertisement;
import com.cdq.model.UserReport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class UserReportExecution implements Serializable {

    private int state;
    private String stateInfo;
    private UserReport userReport;
    private List<UserReport> userReportList;
    private int count;

    public UserReportExecution(BaseStateEnum baseStateEnum) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
    }

    public UserReportExecution(BaseStateEnum baseStateEnum, UserReport userReport) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.userReport = userReport;
    }

    public UserReportExecution(BaseStateEnum baseStateEnum, List<UserReport> userReportList) {
        this.state = baseStateEnum.getState();
        this.stateInfo = baseStateEnum.getStateInfo();
        this.userReportList = userReportList;
    }


}
