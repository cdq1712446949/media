package com.cdq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
 * @description：整合Redis配置类
 * @modified By：
 * @version: 1.0.1
 */
@Configuration
public class RedisConfig {

    /**
     * 配置连接池信息
     *
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(1500);
        //最大空闲连接数
        jedisPoolConfig.setMaxIdle(1500);
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(500);
        //获取连接时最大等待时间
        jedisPoolConfig.setMaxWaitMillis(20000);
        //获取连接时检查是否可用
        jedisPoolConfig.setTestOnBorrow(true);
        //返回连接时检查是否可用
        jedisPoolConfig.setTestOnReturn(true);
        //是否开启空闲资源监测
        jedisPoolConfig.setTestWhileIdle(true);
        //-1不检测   单位为毫秒  空闲资源监测周期
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(300000);
        //资源池中资源最小空闲时间 单位为毫秒  达到此值后空闲资源将被移除
        jedisPoolConfig.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
        //做空闲监测时，每次采集的样本数  -1代表对所有连接做监测
        jedisPoolConfig.setNumTestsPerEvictionRun(300);
        return jedisPoolConfig;
    }

    /**
     * 创建连接工厂
     *
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).and().readTimeout(Duration.ofMillis(2000)).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setPort(6381);
        redisStandaloneConfiguration.setHostName("localhost");
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

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
     * @param slaveRedisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> slaveRedisTemplate) {
        return slaveRedisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param slaveRedisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> slaveRedisTemplate) {
        return slaveRedisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param slaveRedisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> slaveRedisTemplate) {
        return slaveRedisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param slaveRedisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> slaveRedisTemplate) {
        return slaveRedisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param slaveRedisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> slaveRedisTemplate) {
        return slaveRedisTemplate.opsForZSet();
    }

}
