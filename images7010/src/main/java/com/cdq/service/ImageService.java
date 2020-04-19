package com.cdq.service;

import com.cdq.dto.ImageExecution;
import com.cdq.model.Photo;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/14 18:33
 * @description：iamge接口
 * @modified By：
 * @version: $
 */
public interface ImageService {

    /**
     * 添加图片记录
     * @param photo
     * @return
     */
    ImageExecution addImage(Photo photo);

}
