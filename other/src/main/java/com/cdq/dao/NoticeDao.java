package com.cdq.dao;

import com.cdq.model.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDao {

    /**
     * 获取状态为0的公告
     * @return
     */
    List<Notice> queryNoticeList(@Param("notice") Notice notice, @Param("rowIndex") int page,
                                 @Param("pageSize") int pageSize);

    /**
     * 插入新的公告
     * @param notice
     * @return
     */
    int insertNotice(@Param("notice") Notice notice);

    /**
     * 修改公告记录（状态，内容，最后修改时间）
     * @param notice
     * @return
     */
    int updateNotice(@Param("notice") Notice notice);

}
