package com.cdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/13 20:13
 * @description：图片服务
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class VideoAppServer {

    public static void main(String[] args) {
        SpringApplication.run(VideoAppServer.class);
    }

}
