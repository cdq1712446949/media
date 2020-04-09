package com.cdq.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/20 15:43
 * @description：配置类
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class AppConfig {

//    @Bean
//    public DriverManagerDataSource getDataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/media?useUnicode=true&characterEncoding=utf8");
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setPassword("980814");
//        dataSource.setUsername("root");
//        return dataSource;
//    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    /**
//     * 配置Hystrix
//     * @return
//     */
//    @Bean
//    public Customizer<ReactiveHystrixCircuitBreakerFactory> defaultConfig() {
//        return factory -> factory.configureDefault(id -> HystrixObservableCommand.Setter
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        .withExecutionTimeoutInMilliseconds(2000)));
//    }


}
