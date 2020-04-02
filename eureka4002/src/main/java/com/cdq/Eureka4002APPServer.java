package com.cdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/13 8:27
 * @description：Eureka3000启动类
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaServer
@EnableCircuitBreaker
public class Eureka4002APPServer {


    public static void main(String[] args) {
        SpringApplication.run(Eureka4002APPServer.class);
    }

}
