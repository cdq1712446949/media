package com.cdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/16 9:45
 * @description：网关启动类
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableZuulProxy
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ZuulServerStart {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerStart.class);
    }

}
