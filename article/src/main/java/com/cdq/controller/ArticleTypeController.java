package com.cdq.controller;

import com.cdq.Service.ArticleTypeService;
import com.cdq.execution.ArticleTypeExecution;
import com.cdq.model.ArticleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/9 14:37
 * @description：处理关于articleType的请求
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    /**
     * 获取所有二级文章类型列表
     *
     * @return
     */
    @RequestMapping("/aflat")
    public Map getAllFirstLevelArticleType(String parentId, String typeId) {
        Map<String, Object> modelMap = new HashMap<>();
        ArticleTypeExecution result = null;
        boolean isHave = false;
        //条件组合
        ArticleType articleType = new ArticleType();
        if (parentId != null && !parentId.equals("")) {
            ArticleType parentType = new ArticleType();
            parentType.setArticleTypeId(Short.valueOf(parentId));
            articleType.setParentArticleType(parentType);
            isHave = true;
        }
        if (typeId != null && !typeId.equals("")) {
            articleType.setArticleTypeId(Short.valueOf(typeId));
            isHave = true;
        }
        //根据条件选择service方法
        if (isHave) {
            result = articleTypeService.getArticleTypeList(articleType);
        }else {
            result = articleTypeService.getFirstArticleTypeList();
        }
        if (result.getState() == 0) {
            modelMap.put("success", true);
            modelMap.put("firstLevelList", result.getArticleTypeList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", result.getStateInfo());
        }
        return modelMap;
    }

}
