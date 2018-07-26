package com.ycn.springcloud.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * redis工具类
 *
 * @author ycn
 * @package com.ycn.springcloud.util
 * @ClassName RedisUtil
 * @Date 2018/7/25 15:08
 */
@Component
public class RedisUtil {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    private static final String SET_OK_MAS = "OK";
    private static final Long DEL_OK_MSG = 1L;

    private static final String SET_IF_NOT_EXIST = "NX";//只在键不存在的时候设置键
    private static final String SET_WITH_EXPIRE_TIME = "PX";//设置指定到期时间（毫秒为单位)

    /**
     * time millisecond
     */
    private static final int MILL_SECOND = 1000;

    /**
     * 获取redis连接
     * @return
     */
    private Object getRedisConnection() {
        Object connection ;
        RedisConnection redisConnection = jedisConnectionFactory.getConnection();
        if (null != redisConnection){
            connection = redisConnection.getNativeConnection();
        }else {
            RedisClusterConnection clusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = clusterConnection.getNativeConnection() ;
        }
        return connection;
    }

    /**
     * 插入值
     * @param key
     * @param val
     */
    public boolean set(String key, String val) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                if(SET_OK_MAS.equals(((Jedis) connection).set(key, val)))
                    return true;
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            if(SET_OK_MAS.equals(((JedisCluster) connection).set(key, val)))
                return true;
        }
        return false;
    }
    public void set(String key, List<?> vals) {
        Object connection = getRedisConnection();
        if(!CollectionUtils.isEmpty(vals)){
            if (connection instanceof Jedis) {
                try {
                    for(Object val : vals)
                        ((Jedis) connection).rpush(key, JSON.toJSONString(val));
                }finally {
                    //释放资源
                    ((Jedis) connection).close();
                }
            }else {
                for(Object val : vals)
                    ((JedisCluster) connection).rpush(key, JSON.toJSONString(val));
            }
        }
    }

    /**
     *
     * @param key
     * @param field
     * @param val
     * @return
     */
    public boolean hset(String key,String field,String val) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                if(SET_OK_MAS.equals(((Jedis) connection).hset(key, field, val)))
                    return true;
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            if(SET_OK_MAS.equals(((JedisCluster) connection).hset(key, field, val)))
                return true;
        }
        return false;
    }

    /**
     * 插入值  设置有效期
     * @param key
     * @param val
     * @param timeOut  超时时间  单位S
     * @return
     */
    public boolean set(String key, String val, int timeOut) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                if(SET_OK_MAS.equals(((Jedis) connection).setex(key, timeOut, val)))
                    return true;
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            if(SET_OK_MAS.equals(((JedisCluster) connection).setex(key, timeOut, val)))
                return true;
        }
        return false;
    }

    /**
     * 设置临时且唯一（生成token）
     * @param prefix  业务前缀
     * @param key
     * @param val
     * @param timeOut  有效期  单位：秒
     * @return
     */
    public boolean set(String prefix, String key, String val, int timeOut) {
        Object connection = getRedisConnection();
        String result ;
        if (connection instanceof Jedis){
            result =  ((Jedis) connection).set(prefix + key, val, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeOut * MILL_SECOND);
            ((Jedis) connection).close();
        }else {
            result = ((JedisCluster) connection).set(prefix + key, val, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeOut * MILL_SECOND);
        }
        if (SET_OK_MAS.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                return ((Jedis) connection).get(key);
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            return ((JedisCluster) connection).get(key);
        }
    }

    public Object get(String key) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                return ObjectUtil.unserialize(((Jedis) connection).get(key.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                return null;
            } finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            try {
                return ObjectUtil.unserialize(((JedisCluster) connection).get(key.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }

    /**
     *
     * @param key
     * @param clazz  目标对象
     * @param <T>
     * @return
     */
    public <T> List<T> get(String key, Class<T> clazz) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                List<String> datas = ((Jedis) connection).lrange(key, 0, -1);
                if (!CollectionUtils.isEmpty(datas)) {
                    List<T> res = new ArrayList<>();
                    for (String data : datas)
                        res.add(JSON.parseObject(data, clazz));
                    return res;
                }
                return null;
            } finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            List<String> datas = ((JedisCluster) connection).lrange(key, 0, -1);
            if (!CollectionUtils.isEmpty(datas)) {
                List<T> res = new ArrayList<>();
                for (String data : datas)
                    res.add(JSON.parseObject(data, clazz));
                return res;
            }
            return null;
        }
    }
    public String hget(String key, String field) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                return ((Jedis) connection).hget(key, field);
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            return ((JedisCluster) connection).hget(key, field);
        }
    }
    public Map<String, String> hgetAll(String key) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                return ((Jedis) connection).hgetAll(key);
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            return ((JedisCluster) connection).hgetAll(key);
        }
    }

    /**
     * 判断是否存在指定的key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                return ((Jedis) connection).exists(key);
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            return ((JedisCluster) connection).exists(key);
        }
    }

    /**
     * 删除指定key
     * @param key
     */
    public boolean del(String key) {
        Object connection = getRedisConnection();
        if (connection instanceof Jedis) {
            try {
                if(DEL_OK_MSG < ((Jedis) connection).del(key))
                    return true;
            }finally {
                //释放资源
                ((Jedis) connection).close();
            }
        }else {
            if(0 < ((JedisCluster) connection).del(key))
                return true;
        }
        return false;
    }
}
