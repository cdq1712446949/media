package com.cdq.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 21:32
 * @description：
 * @modified By：
 * @version: 1.0.1
 */

@Getter
@Setter
@ToString
public class IndexNumber {

    private Integer newUserNumber;
    private Integer newArticleNumber;
    private Integer newImageNumber;
    private Integer newReportNumber;

    public IndexNumber(){

    }

    public IndexNumber(int user,int article,int image,int report){
        this.newArticleNumber = article;
        this.newImageNumber = image;
        this.newReportNumber = report;
        this.newUserNumber = user;
    }

}
