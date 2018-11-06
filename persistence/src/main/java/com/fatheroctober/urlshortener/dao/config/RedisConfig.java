package com.fatheroctober.urlshortener.dao.config;

import com.fatheroctober.urlshortener.dao.model.IUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:redisdb.properties")
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis_host:localhost}")
    private String redisHost;

    @Value("${redis_port:6379}")
    private int redisPort;

    @PostConstruct
    private void init() {
        logger.info("Initialized with Redis host=" + redisHost + ", port=" + redisPort);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setUsePool(true);
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);

        return connectionFactory;
    }


    @Bean
    public RedisTemplate<String, IUrl> redisTemplate() {
        RedisTemplate<String, IUrl> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }
}
