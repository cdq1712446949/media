package com.cdq.dao;

import com.cdq.model.ReportReason;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2019/9/26 22:37
 * @description：举报原因dao层接口
 * @modified By：
 * @version: $
 */
@Repository
public interface ReportReasonDao {

    List<ReportReason> queryParentList();

    List<ReportReason> queryChildList(ReportReason reportReason);

}
