package com.cdq.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.code.kaptcha.util.Config;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Properties;

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

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public DefaultKaptcha getKaptchaServlet(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border","yes");
        properties.setProperty("kaptcha.border.color","105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", String.valueOf(100));
        properties.setProperty("kaptcha.image.height","50");
        properties.setProperty("kaptcha.textproducer.font.size","4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");
        properties.setProperty("kaptcha.noise.color","black");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        properties.setProperty("kaptcha.background.clear.from","185,56,213");
        properties.setProperty("kaptcha.background.clear.to","white");
        properties.setProperty("kaptcha.textproducer.char.space","3");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
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
