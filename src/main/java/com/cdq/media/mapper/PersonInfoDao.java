package com.cdq.media.mapper;

import com.cdq.media.model.PersonInfo;
import org.springframework.stereotype.Repository;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/17 11:20
 * @description：PersonInfo dao层操作
 * @modified By：
 * @version: $
 */
@Repository
public interface PersonInfoDao {

    /**
     * 添加personInfo信息
     * @param personInfo
     * @return
     */
    boolean register(PersonInfo personInfo);

}
