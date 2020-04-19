package com.cdq.controller;

import com.cdq.Service.ArticleService;
import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;
import com.cdq.model.ArticleType;
import com.cdq.model.User;
import com.cdq.until.HttpServletRequestUtil;
import com.cdq.until.ImageHolder;
import com.cdq.until.ImageUtil;
import com.cdq.until.PathUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:37
 * @description：文章数据接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArticleService articleService;

    public static String USER_URL = "http://SERVER-USER";
    public static String IMAGE_URL = "http://SERVER-IMAGE";
    private static final int IMAGEMAXCOUNT = 6;
    public static final String IMAGE_SRC = "D:/projectdev/image/";

    /**
     * 根据请求返回文章列表
     *
     * @return
     */
    @RequestMapping("/getArticle")
    public Map getArticle(String typeId, String indexPage) {
        Map<String, Object> modelMap = new HashMap<>();
        Article article = new Article();
        ArticleExecution result = null;
        // TODO 有时间加上try catch（我是真的不想加,我好懒啊）
        if (typeId != null && !typeId.equals("")) {
            ArticleType articleType = new ArticleType();
            articleType.setArticleTypeId(Short.valueOf(typeId));
            article.setArticleType(articleType);
            result = articleService.getArticleList(article, Integer.parseInt(indexPage), 10);
        } else {
            result = articleService.getArticleList(article, Integer.parseInt(indexPage), 10);
        }
        if (result.getState() == 0) {
            modelMap.put("success", true);
            modelMap.put("articleList", result.getArticleList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", result.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 文章发布接口
     * 先处理文章数据，创建文章对象，保存到数据库并且获取文章id
     * 然后处理文章图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public Map addArticle(HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        if (productStr != null|| !productStr.equals("")){
            Article article = objectMapper.readValue(productStr, Article.class);
            //获取文章发布者信息
            // TODO 获取token中的id
            User user = new User();
            user.setUserId("18271579008757346");
            article.setUser(user);
            //添加文章记录
            ArticleExecution articleExecution = articleService.addArticle(article);
            if (articleExecution.getState() != 0){
                modelMap.put("success",false);
                modelMap.put("errMsg",articleExecution.getStateInfo());
                return modelMap;
            }
            MultipartHttpServletRequest multipartRequest = null;
            List<String> productImgs = new ArrayList<>();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            try {
                if (multipartResolver.isMultipart(request)) {
                    multipartRequest = (MultipartHttpServletRequest) request;
                    for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                        CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                                .getFile("productImg" + i);
                        if (productImg != null) {
                            productImgs.add(addThumils(article, productImg));
                        }
                    }
                    HashMap result = restTemplate.getForObject(IMAGE_URL+"/imgHouse/article?pojoId="+article.getArticleId()+"&imgSrcs="+objectMapper.writeValueAsString(productImgs),
                            HashMap.class);
                    //对结果进行处理
                    if ((boolean)result.get("success")){
                        modelMap.put("success",true);
                        modelMap.put("articleId",article.getArticleId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","信息不全");
        }
        return modelMap;
    }

    /**
     * 修改文章接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
    public Map modifyArticle(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        return modelMap;
    }


    @RequestMapping("/getUser")
    public Map getUser() {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("userInfo", restTemplate.getForObject(IMAGE_URL + "/getUser.do", Object.class));
        return modelMap;
    }

    public String addThumils(Article article, CommonsMultipartFile multipartFile) throws IOException {
        ImageHolder imageHolder = new ImageHolder(multipartFile.getFileItem().getName(), multipartFile.getInputStream());
        String dest = PathUtil.getShopImagePath(String.valueOf(article.getArticleId()));
        String productImageAddr = ImageUtil.generateThumbnails(imageHolder, dest);
        return  productImageAddr;
    }

}
