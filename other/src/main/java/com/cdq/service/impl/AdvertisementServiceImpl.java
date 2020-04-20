package com.cdq.service.impl;

import com.cdq.dao.AdvertisementDao;
import com.cdq.dto.AdvertisementExecution;
import com.cdq.dto.RedisCache;
import com.cdq.enums.AdvertisementStateEnum;
import com.cdq.model.Advertisement;
import com.cdq.service.AdvertisementService;
import com.cdq.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:08
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {


    @Autowired
    private AdvertisementDao advertisementDao;

    /**
     * 管理员调用的查询方法
     *
     * @param advertisement
     * @return
     */
    @Override
    public AdvertisementExecution getAdvertisementList(Advertisement advertisement, int page, int pageSize) {
        List<Advertisement> list = advertisementDao.queryAdvertisement(
                advertisement,PageUtil.pageToRowIndex(page,pageSize),pageSize);
        return new AdvertisementExecution(AdvertisementStateEnum.SUCCESS, list);
    }

    /**
     * 添加广告记录
     * advertisement_href，advertisement_photo，advertisement_name属性值不能为空
     *
     * @param advertisement
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "ugal" , allEntries = true)
    public AdvertisementExecution addAdvertisement(Advertisement advertisement,String delKey) {
        //参数校验
        if (advertisement.getAdvertisementHref() != null && !advertisement.getAdvertisementHref().equals("")) {
            if (advertisement.getAdvertisementName() != null && !advertisement.getAdvertisementName().equals("")) {
                if (advertisement.getAdvertisementPhoto() != null && !advertisement.getAdvertisementPhoto().equals("")) {
                    //添加创建时间属性值
                    advertisement.setAdvertisementCreateTime(new Date());
                    //请求数据库添加纪录
                    try {
                        int result=advertisementDao.insertAdvertisement(advertisement);
                        if (result>0){
                            return new AdvertisementExecution(AdvertisementStateEnum.SUCCESS);
                        }else {
                            return new AdvertisementExecution(AdvertisementStateEnum.INNER_ERROR);
                        }
                    }catch (Exception e){
                        throw new RuntimeException("数据库添加广告记录失败，错误信息："+e.getMessage());
                    }
                } else {
                    return new AdvertisementExecution(AdvertisementStateEnum.EMPTY_INFO);
                }
            } else {
                return new AdvertisementExecution(AdvertisementStateEnum.EMPTY_INFO);
            }
        } else {
            return new AdvertisementExecution(AdvertisementStateEnum.EMPTY_INFO);
        }
    }

    /**
     * 修改广告记录
     * id不能为空
     * @param advertisement
     * @return
     */
    @Transactional
    @Override
    @CacheEvict(value = "ugal" , allEntries = true)
    public AdvertisementExecution changeAdvertisement(Advertisement advertisement) {
        //校验参数
        if (advertisement.getAdvertisementId()==null||advertisement.getAdvertisementId()==0){
            return new AdvertisementExecution(AdvertisementStateEnum.EMPTY_ID);
        }
        //添加最后修改时间属性值
        advertisement.setAdvertisementLastEditTime(new Date());
        //修改数据库记录
        try {
            int result=advertisementDao.updateAdvertisement(advertisement);
            if (result==1){
                return new AdvertisementExecution(AdvertisementStateEnum.SUCCESS);
            }else {
                return new AdvertisementExecution(AdvertisementStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            throw new RuntimeException("修改广告记录失败，错误信息："+e.getMessage());
        }
    }


    @Cacheable(value = "ugal" , key = "#cacheKey")
    @Override
    public AdvertisementExecution getAdvertisementListUser(String cacheKey) {
        Advertisement advertisement=new Advertisement();
        //用户只能获取正常状态下的广告记录
        advertisement.setAdvertisementStatus((byte) 0);
        List<Advertisement> list=advertisementDao.queryAdvertisement(advertisement,0,1);

        return new AdvertisementExecution(AdvertisementStateEnum.SUCCESS,list);
    }

}
