package com.ycn.redistool.lock;

import com.ycn.redistool.constant.RedisToolsConstant;
import com.ycn.redistool.util.ScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * Redis分布式锁
 *  基本特性：
 *      1、高性能（加、解锁高性能）
 *      2、可以使用阻塞锁和费阻塞锁
 *      3、不能出现死锁
 *      4、可用性（不能出现节点down掉后加锁失败）
 *  实现原理：
 *      Redis set key 时的一个 NX 参数可以保证在这个 key 不存在的情况下写入成功；
 *      并且再加上 EX 参数可以让该 key 在超时之后自动删除；
 *      所以可以保证在同一时刻只会有一个进程获得锁，并且不会出现死锁（最坏的情况为即为超时后自动删除）。
 *
 * @author ycn
 * @package com.ycn.redistool.lock
 * @ClassName RedisLock
 * @Date 2018/7/20 22:55
 */
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final String LOCK_MSG = "OK";
    private static final Long UNLOCK_MSG = 1L;

    private static final String SET_IF_NOT_EXIST = "NX";//只在键不存在的时候设置键
    private static final String SET_WITH_EXPIRE_TIME = "PX";//设置指定到期时间（毫秒为单位)

    private String lockPrefix;

    private int sleepTime;

    private JedisConnectionFactory jedisConnectionFactory;
    private int type ;

    /**
     * time millisecond
     */
    private static final int TIME = 1000;

    /**
     * lua script
     */
    private String script;

    private RedisLock(Builder builder) {
        this.jedisConnectionFactory = builder.jedisConnectionFactory;
        this.type = builder.type ;
        this.lockPrefix = builder.lockPrefix;
        this.sleepTime = builder.sleepTime;
        buildScript();
    }

    /**
     * 加载脚本文件
     */
    private void buildScript() {
        script = ScriptUtil.getScript("lock.lua");
    }

    /**
     * 获取Redis连接
     * @return
     */
    private Object getRedisConnection() {
        Object connection ;
        if (type == RedisToolsConstant.SINGLE){
            RedisConnection redisConnection = jedisConnectionFactory.getConnection();
            connection = redisConnection.getNativeConnection();
        }else {
            RedisClusterConnection clusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = clusterConnection.getNativeConnection() ;
        }
        return connection;
    }

    /**
     * 非阻塞锁
     * @param key
     * @param request  锁的存储值
     * @return  true：加锁成功  false：加锁失败
     */
    public boolean tryNonBlockingLock(String key, String request) {
        Object connection = getRedisConnection();
        String result ;
        if (connection instanceof Jedis){
            result =  ((Jedis) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            ((Jedis) connection).close();
        }else {
            result = ((JedisCluster) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
        }
        if (LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 非阻塞锁  自定义失效时间
     * @param key
     * @param request  存储的值
     * @param expireTime  有效期
     * @return
     */
    public boolean tryNonBlockingLock(String key, String request, int expireTime) {
        Object connection = getRedisConnection();
        String result ;
        if (connection instanceof Jedis){
            result = ((Jedis) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            ((Jedis) connection).close();
        }else {
            result = ((JedisCluster) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        }
        if (LOCK_MSG.equals(result))
            return true;
        return false;
    }

    /**
     * 阻塞锁
     * @param key
     * @param request  锁存储的值
     * @return
     */
    public void tryBlockingLock(String key, String request) throws InterruptedException {
        Object connection = getRedisConnection();
        String result ;
        for (; ;) {
            if (connection instanceof Jedis){
                result = ((Jedis)connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
                if (LOCK_MSG.equals(result)){
                    ((Jedis) connection).close();
                }
            }else {
                result = ((JedisCluster)connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            }
            if (LOCK_MSG.equals(result)) {
                break;
            }
            Thread.sleep(sleepTime);
        }
    }

    /**
     * 阻塞锁 自定义阻塞时间
     * @param key
     * @param request  锁的存储值
     * @param blockTime  阻塞时间
     * @return
     */
    public boolean tryBlockingLock(String key, String request, int blockTime) throws InterruptedException {
        Object connection = getRedisConnection();
        String result ;
        while (blockTime >= 0) {
            if (connection instanceof Jedis){
                result = ((Jedis) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME) ;
                if (LOCK_MSG.equals(result)){
                    ((Jedis) connection).close();
                }
            }else {
                result = ((JedisCluster) connection).set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME) ;
            }
            if (LOCK_MSG.equals(result)) {
                return true;
            }
            blockTime -= sleepTime;
            Thread.sleep(sleepTime);
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param request
     * @return
     */
    public boolean unlock(String key, String request) {
        Object connection = getRedisConnection();
        Object result = null;
        if (connection instanceof Jedis) {
            result = ((Jedis) connection).eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(request));
            ((Jedis) connection).close();
        } else if (connection instanceof JedisCluster) {
            result = ((JedisCluster) connection).eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(request));
        } else {
            return false;
        }
        if (UNLOCK_MSG.equals(result))
            return true;
        return false;
    }

    public static class Builder{
        private static final String DEFAULT_LOCK_PREFIX = "lock_";

        private static final int DEFAULT_SLEEP_TIME = 100;

        private JedisConnectionFactory jedisConnectionFactory = null;

        private int type;

        private String lockPrefix = DEFAULT_LOCK_PREFIX;
        private int sleepTime = DEFAULT_SLEEP_TIME;

        public Builder(JedisConnectionFactory jedisConnectionFactory, int type) {
            this.jedisConnectionFactory = jedisConnectionFactory;
            this.type = type;
        }

        public Builder lockPrefix(String lockPrefix) {
            this.lockPrefix = lockPrefix;
            return this;
        }

        public Builder sleepTime(int sleepTime) {
            this.sleepTime = sleepTime;
            return this;
        }

        public RedisLock build() {
            return new RedisLock(this);
        }
    }

}
