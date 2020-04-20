package com.cdq.service;

import com.cdq.dto.NoticeExecution;
import com.cdq.model.Notice;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:08
 * @description：
 * @modified By：
 * @version: $
 */
public interface NoticeService {

    /**
     * 获取公告列表
     * @param notice
     * @return
     */
    NoticeExecution getNoticeList(Notice notice, int page, int pageSize,String cacheKey);

    /**
     * 添加公告
     * @param notice
     * @return
     */
    NoticeExecution addNotice(Notice notice,String delKey);

    /**
     * 修改公告记录
     * @param notice
     * @return
     */
    NoticeExecution changeNotice(Notice notice);

}
