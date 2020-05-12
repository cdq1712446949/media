package com.cdq.dao;

import com.cdq.model.UserReport;
import org.springframework.stereotype.Repository;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2019/9/27 8:13
 * @description：用户举报dao层接口
 * @modified By：
 * @version: $
 */
@Repository
public interface UserReportDao {

    int insertUserReport(UserReport userReport);

}
