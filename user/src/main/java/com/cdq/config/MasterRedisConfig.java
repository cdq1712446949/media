package com.cdq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 9:28
 * @description：redis主服务器，只负责写操作
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class MasterRedisConfig {

   @Autowired
   private JedisPoolConfig jedisPoolConfig;

    /**
     * 创建连接工厂
     *
     * @param jedisPoolConfig
     * @return
     */
    @Bean(name = "masterRedisConfig")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).and().readTimeout(Duration.ofMillis(2000)).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setPort(6381);
        redisStandaloneConfiguration.setHostName("localhost");
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean(name = "masterRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory masterRedisConfig) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(masterRedisConfig);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        jacksonSeial.setObjectMapper(om);
        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 对hash类型的数据操作
     * @param masterRedisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> masterRedisTemplate) {
        return masterRedisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param masterRedisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> masterRedisTemplate) {
        return masterRedisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param masterRedisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> masterRedisTemplate) {
        return masterRedisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param masterRedisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> masterRedisTemplate) {
        return masterRedisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param masterRedisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> masterRedisTemplate) {
        return masterRedisTemplate.opsForZSet();
    }

}