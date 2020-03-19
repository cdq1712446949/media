package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:35
 * @description：Article模块启动类
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class ArticleAppServer {

    public static void main(String[] args) {
        SpringApplication.run(ArticleAppServer.class);
    }

}
