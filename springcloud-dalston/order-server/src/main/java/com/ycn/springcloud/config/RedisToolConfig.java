package com.ycn.springcloud.config;

import com.ycn.redistool.constant.RedisToolsConstant;
import com.ycn.redistool.limit.RedisLimit;
import com.ycn.redistool.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author ycn
 * @package com.ycn.springcloud.config
 * @ClassName RedisLockConfig
 * @Date 2018/7/22 16:23
 */
@Configuration
public class RedisToolConfig {

    @Value("${redis.limit}")
    private int limit;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisLock redisLockBuild() {
        RedisLock redisLock = new RedisLock.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .lockPrefix("lock_")
                .sleepTime(100)
                .build();
        return redisLock;
    }

    @Bean
    public RedisLimit redisLimitBuild() {
        RedisLimit redisLimit = new RedisLimit.Builder(jedisConnectionFactory, RedisToolsConstant.SINGLE)
                .limit(limit)
                .build();
        return redisLimit;
    }
}
