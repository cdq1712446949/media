package com.cdq.media.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 15:17
 * @description：SqlSessionFactory配置类
 * @modified By：
 * @version: 1.0.1
 */
//@Configuration
public class SqlSessionFactoryConfiguration {

    //注入dataSource实体类
    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSessionFactory(){
        //创建实例
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        //配置dataSource
        sqlSessionFactory.setDataSource(dataSource);
        return sqlSessionFactory;
    }


}
