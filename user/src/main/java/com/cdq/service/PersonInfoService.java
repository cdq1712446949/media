package com.cdq.service;

import com.cdq.execution.PersonInfoExecution;
import com.cdq.model.PersonInfo;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/9 16:12
 * @description：
 * @modified By：
 * @version: $
 */
public interface PersonInfoService {

    /**
     * 查询用户账号设置信息
     * @param personInfo
     * @return
     */
    PersonInfoExecution selectPersonInfo(PersonInfo personInfo);

    /**
     * 修改账号设置
     * @param personInfo
     * @return
     */
    PersonInfoExecution editPersonInfo(PersonInfo personInfo);

}
