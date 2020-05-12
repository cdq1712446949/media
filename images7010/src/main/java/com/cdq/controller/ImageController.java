package com.cdq.controller;

import com.cdq.dto.ImageExecution;
import com.cdq.enums.BaseStateEnum;
import com.cdq.model.Article;
import com.cdq.model.Photo;
import com.cdq.model.UserReport;
import com.cdq.service.ImageService;
import com.cdq.util.HttpServletRequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/13 20:25
 * @description：image服务接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping("/imgHouse")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 接收图片文件，保存在本地并且向数据库添加图片记录
     *
     * @param request
     * @return modelMap :图片引用地址
     */
    @RequestMapping(value = "/article")
    public Map imgHouse(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String idStr = HttpServletRequestUtil.getString(request, "pojoId");
        String imgListStr = HttpServletRequestUtil.getString(request, "imgSrcs");
        try {
            short temp = 1;
            String[] imgsrcs = objectMapper.readValue(imgListStr, String[].class);
            for (String src : imgsrcs){
                Photo photo = new Photo();
                Article article = new Article();
                article.setArticleId(Integer.valueOf(idStr));
                photo.setArticle(article);
                photo.setPhotoAddr(src);
                photo.setPhotoNum(temp++);
                ImageExecution result = imageService.addImage(photo);
                if (result.getState() != 0){
                    modelMap.put("success",false);
                    modelMap.put("errMsg",result.getStateInfo());
                    return modelMap;
                }
            }
            modelMap.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", "String转POJO失败:" + e.getMessage());
        }
        return modelMap;
    }

    /**
     * 接收图片文件，保存在本地并且向数据库添加图片记录
     *
     * @param request
     * @return modelMap :图片引用地址
     */
    @RequestMapping(value = "/report")
    public Map reportimg(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String idStr = HttpServletRequestUtil.getString(request, "pojoId");
        String imgListStr = HttpServletRequestUtil.getString(request, "imgSrcs");
        try {
            short temp = 1;
            String[] imgsrcs = objectMapper.readValue(imgListStr, String[].class);
            for (String src : imgsrcs){
                Photo photo = new Photo();
                UserReport userReport = new UserReport();
                userReport.setUserReportId(Integer.valueOf(idStr));
                photo.setUserReport(userReport);
                photo.setPhotoAddr(src);
                photo.setPhotoNum(temp++);
                ImageExecution result = imageService.addImage(photo);
                if (result.getState() != 0){
                    modelMap.put("success",false);
                    modelMap.put("errMsg",result.getStateInfo());
                    return modelMap;
                }
            }
            modelMap.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", "String转POJO失败:" + e.getMessage());
        }
        return modelMap;
    }


}
