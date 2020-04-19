package com.cdq.service.impl;

import com.cdq.dao.ImageDao;
import com.cdq.dto.ImageExecution;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Photo;
import com.cdq.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/14 18:34
 * @description：Image实现类
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    @Transactional
    public ImageExecution addImage(Photo photo) {
        //检验参数(文章，广告，举报三选一，必须有，但是我不想写代码)
        //添加创建时间
        photo.setPhotoCreateTime(new Date());
        int result = imageDao.insertImage(photo);
        if (result == 0){
            return new ImageExecution(BaseStateEnum.INNER_ERROR);
        }else {
            return new ImageExecution(BaseStateEnum.SUCCESS);
        }
    }
}
