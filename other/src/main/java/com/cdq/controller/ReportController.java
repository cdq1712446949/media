package com.cdq.controller;

import com.cdq.dto.ReportReasonExecution;
import com.cdq.dto.UserReportExecution;
import com.cdq.model.Article;
import com.cdq.model.ReportReason;
import com.cdq.model.User;
import com.cdq.model.UserReport;
import com.cdq.service.ReportReasonService;
import com.cdq.service.UserReportService;
import com.cdq.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/25 14:38
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ReportController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserReportService userReportService;
    @Autowired
    private ReportReasonService reportReasonService;

    @RequestMapping(value = "/uaup",method = RequestMethod.POST)
    public Map addUserReprot(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        //接收参数
        UserReport userReport = (UserReport) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request,ConstansUtil.REPORT_STR),
                UserReport.class);
        User user = ObjectUtil.getUserId(request);
        userReport.setReportUser(user);
        //调用service层
        UserReportExecution result = userReportService.addUserReport(userReport);
        //处理图片
        MultipartHttpServletRequest multipartRequest = null;
        List<String> reportImgs = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                //处理图片文件
                for (int i = 0; i < 3; i++) {
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                            .getFile("reportImg" + i);
                    if (productImg != null) {
                        reportImgs.add(addThumils(userReport, productImg));
                    }else{
                        break;
                    }
                }
                //访问图片服务，添加图片记录
                HashMap result1 = restTemplate.getForObject(
                        ConstansUtil.IMAGE_URL + "/imgHouse/report?pojoId=" + userReport.getUserReportId() + "&&imgSrcs=" + objectMapper.writeValueAsString(reportImgs),
                        HashMap.class);
                //对结果进行处理
                if ((boolean) result1.get(ConstansUtil.SUCCESS)) {
                    modelMap.put(ConstansUtil.SUCCESS, true);
                } else {
                    return result1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "ugprl",method = RequestMethod.POST)
    public Map getParentReasonList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        ReportReasonExecution result = reportReasonService.selectParentList();
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.REASON_LIST,result.getReportReasonList());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "ugcrl",method = RequestMethod.POST)
    public Map getChildReasonList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //接收参数
        int parentId = HttpServletRequestUtil.getInt(request,ConstansUtil.PARENT_ID);
        ReportReason reportReason = new ReportReason();
        ReportReason parentReason = new ReportReason();
        parentReason.setReportReasonId((byte) parentId);
        reportReason.setParentReason(parentReason);
        ReportReasonExecution result = reportReasonService.selectChildList(reportReason);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.REASON_LIST,result.getReportReasonList());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    private String addThumils(UserReport userReport, CommonsMultipartFile multipartFile) throws IOException {
        ImageHolder imageHolder = new ImageHolder(multipartFile.getFileItem().getName(), multipartFile.getInputStream());
        String dest = PathUtil.getShopImagePath(String.valueOf(userReport.getUserReportId()));
        return ImageUtil.generateThumbnails(imageHolder, dest);
    }

}
