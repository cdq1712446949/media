package com.cdq.config;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/6 14:43
 * @description：配置类，生成注入类
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }




}
