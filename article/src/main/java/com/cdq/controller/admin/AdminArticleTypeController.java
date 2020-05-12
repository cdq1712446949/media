package com.cdq.controller.admin;

import com.cdq.Service.ArticleTypeService;
import com.cdq.execution.ArticleTypeExecution;
import com.cdq.model.ArticleType;
import com.cdq.until.ConstansUtil;
import com.cdq.until.HttpServletRequestUtil;
import com.cdq.until.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 23:44
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RequestMapping("/admin")
@RestController
public class AdminArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @RequestMapping(value = "/agat" ,method = RequestMethod.POST)
    public Map getArticleType(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        ArticleType articleType = new ArticleType();
        String typeName = HttpServletRequestUtil.getString(request,"typeName");
        if (typeName!=null&&!ConstansUtil.EMPTY_STR.equals(typeName)){
            articleType.setArticleTypeName(typeName);
        }
        //调用service层
        ArticleTypeExecution result = articleTypeService.getArticleTypeList(articleType,false);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put(ConstansUtil.ARTICLE_TYPE_LIST,result.getArticleTypeList());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/aaat" , method = RequestMethod.POST)
    public Map addArticleType(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        ArticleType articleType = (ArticleType) ObjectUtil.toPojo(
                HttpServletRequestUtil.getString(request,"typeStr"),ArticleType.class);
        ArticleTypeExecution result = articleTypeService.addArticleType(articleType);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/adats" ,method = RequestMethod.POST)
    public Map delArticleTypes(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        ArrayList typeList = (ArrayList) ObjectUtil.toPojo(
                HttpServletRequestUtil.getString(request,"typeIds"),ArrayList.class);
        ArticleTypeExecution result = articleTypeService.delArticleType(typeList);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

}
