package com.cdq.dao;

import com.cdq.dto.ImageExecution;
import com.cdq.model.Photo;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/13 20:24
 * @description：image服务dao层接口
 * @modified By：
 * @version: $
 */
@Repository
public interface ImageDao {

    /**
     * 添加图片记录
     * @param photo
     * @return
     */
    int insertImage(Photo photo);

}
