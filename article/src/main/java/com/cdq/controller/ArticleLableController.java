package com.cdq.controller;

import com.cdq.Service.ArticleLableService;
import com.cdq.execution.ArticleLableExecution;
import com.cdq.until.ConstansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/28 9:00
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
public class ArticleLableController {

    @Autowired
    private ArticleLableService articleLableService;

    /**
     * 文章话题接口
     * @return
     */
    @RequestMapping(value = "/ugall" , method = RequestMethod.POST)
    public Map getArticleLableList(){
        Map<String,Object> modelMap = new HashMap<>();
        ArticleLableExecution result = articleLableService.getArtiLable();
        if (result.getState() == 0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.ARTICLE_LABLE_LIST,result.getLableList());
        }else{
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

}
