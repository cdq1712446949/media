package com.cdq.service.impl;

import com.cdq.dao.PersonInfoDao;
import com.cdq.enums.BaseStateEnum;
import com.cdq.execution.PersonInfoExecution;
import com.cdq.model.PersonInfo;
import com.cdq.service.PersonInfoService;
import com.cdq.util.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/9 16:12
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    /**
     * 查询账户设置信息
     *
     * @param personInfo
     * @return
     */
    @Override
    public PersonInfoExecution selectPersonInfo(PersonInfo personInfo) {
        //参数校验
        if (personInfo.getUserId() == null || ConstansUtil.EMPTY.equals(personInfo.getUserId())) {
            return new PersonInfoExecution(BaseStateEnum.EMPTY_INFO);
        }
        //调用dao层
        PersonInfo result = personInfoDao.queryPersonInfo(personInfo);
        if (result != null) {
            return new PersonInfoExecution(BaseStateEnum.SUCCESS, result);
        } else {
            return new PersonInfoExecution(BaseStateEnum.INNER_ERROR);
        }
    }

    /**
     * 修改账户设置
     * @param personInfo
     * @return
     */
    @Override
    public PersonInfoExecution editPersonInfo(PersonInfo personInfo) {
        //参数校验
        if (personInfo.getUserId() == null || ConstansUtil.EMPTY.equals(personInfo.getUserId())) {
            return new PersonInfoExecution(BaseStateEnum.EMPTY_INFO);
        }
        //私信状态值校验
        if (personInfo.getSecretStatus()!=null){
            if (personInfo.getSecretStatus()!=PersonInfo.SECRET_NO&&personInfo.getSecretStatus()!=PersonInfo.SECRET_YES){
                return new PersonInfoExecution(BaseStateEnum.ILLEGAL_PARAMETER);
            }
        }
        //调用dao层
        int result = personInfoDao.updatePersonInfo(personInfo);
        if (result != 0) {
            return new PersonInfoExecution(BaseStateEnum.SUCCESS);
        } else {
            return new PersonInfoExecution(BaseStateEnum.INNER_ERROR);
        }
    }
}
