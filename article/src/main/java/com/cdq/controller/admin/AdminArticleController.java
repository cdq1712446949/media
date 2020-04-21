package com.cdq.controller.admin;

import com.cdq.Service.ArticleService;
import com.cdq.execution.ArticleExecution;
import com.cdq.model.Article;
import com.cdq.until.ConstansUtil;
import com.cdq.until.HttpServletRequestUtil;
import com.cdq.until.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @version : 1.0.1
 * @date ：Created in 2020/4/20 20:56
 * @description ：管理员接口
 * @modified By
 */
@RestController
@RequestMapping("/admin")
public class AdminArticleController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArticleService articleService;


    /**
     * 管理员文章列表接口
     * 查询条件：
     * 1.根据内容查询:articleContent
     * 2.根据创建时间：articleCreateTime(开始时间和结束时间)
     * 3.根据状态：articleStatus
     * 4.
     *
     * @return
     */
    @RequestMapping("/agal")
    public Map getArticle(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String artiStr = HttpServletRequestUtil.getString(request, "artiStr");
        Article article = (Article) ObjectUtil.toPojo(artiStr, Article.class);
        int indexPage = HttpServletRequestUtil.getInt(request, "indexPage");
        ArticleExecution result = articleService.getArticleList(article, indexPage, 10);
        if (result.getState() == 0) {
            modelMap.put(ConstansUtil.SUCCESS, true);
            modelMap.put("articleList", result.getArticleList());
        } else {
            modelMap.put(ConstansUtil.SUCCESS, false);
            modelMap.put("errMsg", result.getStateInfo());
        }
        return modelMap;
    }

}
