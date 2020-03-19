package com.cdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 9:33
 * @description：前端界面请求处理Module
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class ViewAppServer {

    public static void main(String[] args) {
        SpringApplication.run(ViewAppServer.class);
    }

}
