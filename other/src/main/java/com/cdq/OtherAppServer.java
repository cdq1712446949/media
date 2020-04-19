package com.cdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/9 10:20
 * @description：other子项目负责处理广告和公告相关请求
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class OtherAppServer {

    public static void main(String[] args) {
        SpringApplication.run(OtherAppServer.class);
    }

}
