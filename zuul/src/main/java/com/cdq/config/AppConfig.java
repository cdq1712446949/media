package com.cdq.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/7 13:55
 * @description：配置类
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class AppConfig {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
