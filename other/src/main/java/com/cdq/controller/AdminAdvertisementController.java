package com.cdq.controller;

import com.cdq.dto.AdvertisementExecution;
import com.cdq.model.Advertisement;
import com.cdq.service.AdvertisementService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.MultipartUtil;
import com.cdq.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 22:24
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/admin")
public class AdminAdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * 添加广告接口（该接口需要管理员权限才能调用）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addAdver" , method = RequestMethod.POST)
    public Map<String, Object> addAdvertisement(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String adverStr = HttpServletRequestUtil.getString(request, "adverStr");
        try {
            Advertisement advertisement = (Advertisement) ObjectUtil.toPojo(adverStr, Advertisement.class);
            //处理缩略图
            advertisement.setAdvertisementPhoto(MultipartUtil.getImaeUrl(request,ConstansUtil.PEX_ADVER));
            AdvertisementExecution result = advertisementService.addAdvertisement(advertisement,ConstansUtil.ADVER_CACHE_NAME);
            if (result.getState() == 0){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg",result.getStateInfo());
            }
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

}
