package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:30
 * @description：user模块启动类，该类放在com包下的原因：启动类默认扫描该类所在包下的所有子包
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class UserAppServer {

    public static void main(String[] args) {
        SpringApplication.run(UserAppServer.class);
    }

}
