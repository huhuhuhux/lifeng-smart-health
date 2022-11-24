package com.huhuhux.config;


import com.aliyun.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * 创建一个生成JedisPool的工厂
 */
@Configuration
public class JedisPoolFactory {

    @Value("${redis.DB:0}")
    private Integer dbNum;

    @Value("${redis.host:localhost}")
    private String host;

    @Value("${redis.port:6379}")
    private Integer port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout:3000}")
    private Integer timeout;

    @Value("${redis.max-active:20}")
    private Integer maxActive;

    @Value("${redis.max-idle:8}")
    private Integer maxIdle;

    @Value("${redis.min-idle:0}")
    private Integer minIdle;

    @Value("${redis.max-wait:10000}")
    private Long maxWaitMillis;


    @Bean
    public JedisPool generateJedisPoolFactory() {
        if (StringUtils.isBlank(password)) {
            password = null;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, password, dbNum);
        return jedisPool;
    }
}
