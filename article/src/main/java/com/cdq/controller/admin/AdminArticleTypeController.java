package com.cdq.controller.admin;

import com.cdq.Service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
