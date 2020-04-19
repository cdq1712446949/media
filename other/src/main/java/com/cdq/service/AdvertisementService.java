package com.cdq.service;

import com.cdq.dto.AdvertisementExecution;
import com.cdq.model.Advertisement;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:08
 * @description：
 * @modified By：
 * @version: $
 */
public interface AdvertisementService {

    /**
     * 获取广告列表
     * @param advertisement
     * @return
     */
    AdvertisementExecution getAdvertisementList(Advertisement advertisement, int page, int pageSize);

    /**
     * 添加广告(设置权限拦截器)
     * @param advertisement
     * @return
     */
    AdvertisementExecution addAdvertisement(Advertisement advertisement);

    /**
     * 修改广告（设置权限拦截器）
     * @param advertisement
     * @return
     */
    AdvertisementExecution changeAdvertisement(Advertisement advertisement);

    /**
     * 用户获取广告列表(添加缓存)
     * @return
     */
    AdvertisementExecution getAdvertisementListUser();

}