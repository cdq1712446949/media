package com.cdq.dao;

import com.cdq.model.ReportResult;
import com.cdq.model.User;
import com.cdq.model.UserReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    List<UserReport> selectReport(@Param("userReport") UserReport userReport,
                                  @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    int selectReportCount(@Param("userReport") UserReport userReport);

    int insertResult(ReportResult reportResult);

    int updateUserStatus(User user);

    int updateReportStatus(UserReport userReport);

}
