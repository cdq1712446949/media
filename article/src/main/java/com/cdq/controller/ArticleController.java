package com.cdq.controller;

import com.cdq.Service.ArticleService;
import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;
import com.cdq.model.ArticleType;
import com.cdq.until.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @date ：Created in 2020/3/6 14:37
 * @description ：文章数据接口
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArticleService articleService;

    /**
     * 根据请求返回文章列表
     *
     * @return 结果
     */
    @RequestMapping("/getArticle")
    public Map getArticle(HttpServletRequest request, String indexPage) {
        Map<String, Object> modelMap = new HashMap<>();
        //        //参数转化
        Article article = (Article) ObjectUtil.toPojo(HttpServletRequestUtil.getString(request, "artiStr"), Article.class);
        article.setArticleStatus((byte) 0);
        //接收typeId参数
        String typeId = HttpServletRequestUtil.getString(request, "typeId");
        if (typeId != null && !ConstansUtil.EMPTY_STR.equals(typeId)) {
            ArticleType articleType = new ArticleType();
            articleType.setArticleTypeId(Short.valueOf(typeId));
            article.setArticleType(articleType);
        }
        //service层调用
        ArticleExecution result = articleService.getArticleList(article, Integer.parseInt(indexPage), 10);
        resolveResult(result, modelMap);
        return modelMap;
    }

    @RequestMapping(value = "/ugabi", method = RequestMethod.POST)
    public Map getArticleById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        return modelMap;
    }

    /**
     * 文章发布接口
     * 先处理文章数据，创建文章对象，保存到数据库并且获取文章id
     * 然后处理文章图片
     *
     * @param request 参数
     * @return 结果
     */
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public Map addArticle(HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        if (productStr != null || !ConstansUtil.EMPTY_STR.equals(productStr)) {
            Article article = objectMapper.readValue(productStr, Article.class);
            //获取文章发布者信息
            article.setUser(ObjectUtil.getUserId(request));
            //添加文章记录
            ArticleExecution articleExecution = articleService.addArticle(article);
            if (articleExecution.getState() != 0) {
                modelMap.put(ConstansUtil.SUCCESS, false);
                modelMap.put("errMsg", articleExecution.getStateInfo());
                return modelMap;
            }
            MultipartHttpServletRequest multipartRequest = null;
            List<String> productImgs = new ArrayList<>();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            try {
                if (multipartResolver.isMultipart(request)) {
                    multipartRequest = (MultipartHttpServletRequest) request;
                    for (int i = 0; i < ConstansUtil.IMAGEMAXCOUNT; i++) {
                        CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                                .getFile("productImg" + i);
                        if (productImg != null) {
                            productImgs.add(addThumils(article, productImg));
                        }
                    }
                    HashMap result = restTemplate.getForObject(ConstansUtil.IMAGE_URL + "/imgHouse/article?pojoId=" + article.getArticleId() + "&imgSrcs=" + objectMapper.writeValueAsString(productImgs),
                            HashMap.class);
                    //对结果进行处理
                    if ((boolean) result.get(ConstansUtil.SUCCESS)) {
                        modelMap.put(ConstansUtil.SUCCESS, true);
                    } else {
                        return result;
                    }
                    if ((boolean) result.get(ConstansUtil.SUCCESS)) {
                        modelMap.put(ConstansUtil.SUCCESS, true);
                        modelMap.put("articleId", article.getArticleId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                modelMap.put(ConstansUtil.SUCCESS, false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put("errMsg", "信息不全");
        }
        return modelMap;
    }

    @RequestMapping(value = "/ugaal", method = RequestMethod.POST)
    public Map getAttArticle(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String userId = ObjectUtil.getUserId(request).getUserId();
        if (ConstansUtil.EMPTY_STR.equals(userId)) {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, "当前登录已过期，请重新登录");
            modelMap.put(ConstansUtil.ERR_CODE, ConstansUtil.CODE_LOGIN_TIME_OUT);
            return modelMap;
        }
        int indexPage = HttpServletRequestUtil.getInt(request, "indexPage");
        ArticleExecution result = articleService.getAttArticle(userId, indexPage, 10);
        resolveResult(result, modelMap);
        return modelMap;
    }

    @RequestMapping(value = "/ugval", method = RequestMethod.POST)
    public Map getVideoArticle(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //调用service
        ArticleExecution result = articleService.getVideoArticle(new Article(),
                HttpServletRequestUtil.getInt(request, ConstansUtil.INDEX_PAGE), 10);
        resolveResult(result, modelMap);
        return modelMap;
    }

    /**
     * 修改文章接口
     *
     * @param request 请求体
     * @return 结果
     */
    @RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
    public Map modifyArticle(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        return modelMap;
    }


    private String addThumils(Article article, CommonsMultipartFile multipartFile) throws IOException {
        ImageHolder imageHolder = new ImageHolder(multipartFile.getFileItem().getName(), multipartFile.getInputStream());
        String dest = PathUtil.getShopImagePath(String.valueOf(article.getArticleId()));
        return ImageUtil.generateThumbnails(imageHolder, dest);
    }

    private void resolveResult(ArticleExecution result, Map modelMap) {
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
            modelMap.put(ConstansUtil.ARTICLE_LIST, result.getArticleList());
            modelMap.put(ConstansUtil.TOTAL_SIZE, result.getCount());
            modelMap.put(ConstansUtil.TOTAL_PAGE, Math.ceil(result.getCount() / 10));
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put(ConstansUtil.ERRMSG, result.getStateInfo());
        }
    }

}
