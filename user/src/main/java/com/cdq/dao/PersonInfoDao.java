package com.cdq.dao;

import com.cdq.model.PersonInfo;
import org.springframework.stereotype.Repository;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/9 16:00
 * @description：
 * @modified By：
 * @version: $
 */
@Repository
public interface PersonInfoDao {

    /**
     * 通过userId查询perosonInfo
     * @param personInfo
     * @return
     */
    PersonInfo queryPersonInfo(PersonInfo personInfo);

    /**
     * 通过userId修改用户账户设置
     * @param personInfo
     * @return
     */
    int updatePersonInfo(PersonInfo personInfo);

    /**
     * 注册用户账号设置信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);

}
