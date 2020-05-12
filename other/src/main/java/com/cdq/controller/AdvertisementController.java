package com.cdq.controller;

import com.cdq.dto.AdvertisementExecution;
import com.cdq.model.Advertisement;
import com.cdq.service.AdvertisementService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.MultipartUtil;
import com.cdq.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.plugin2.util.PojoUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:07
 * @description：广告请求处理接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * 用户获取获取最后一条插屏广告
     * 不需要前端传递参数
     *
     * @return
     */
    @RequestMapping(value = "/ugal", method = RequestMethod.POST)
    public Map<String, Object> getAdvertismentList() {
        Map<String, Object> modelMap = new HashMap<>();
        AdvertisementExecution advertisementExecution = advertisementService.getAdvertisementListUser(ConstansUtil.ADVER_CACHE_NAME);
        if (advertisementExecution.getState() == 0) {
            modelMap.put("success", true);
            modelMap.put("advertisementList", advertisementExecution.getAdvertisementList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", advertisementExecution.getStateInfo());
        }
        return modelMap;
    }


}
