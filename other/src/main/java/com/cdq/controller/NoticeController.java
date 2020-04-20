package com.cdq.controller;

import com.cdq.dto.AdvertisementExecution;
import com.cdq.dto.NoticeExecution;
import com.cdq.model.Advertisement;
import com.cdq.model.Notice;
import com.cdq.service.NoticeService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:07
 * @description：公告请求接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 添加广告接口（该接口需要管理员权限才能调用）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addNoti")
    public Map<String, Object> addAdvertisement(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String noticeStr = HttpServletRequestUtil.getString(request, "noticeStr");
        try {
            Notice notice = (Notice) ObjectUtil.toPojo(noticeStr, Notice.class);
            NoticeExecution result = noticeService.addNotice(notice,ConstansUtil.NOTICE_CACHE_NAME);
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
