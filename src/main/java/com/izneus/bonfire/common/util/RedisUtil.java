package com.izneus.bonfire.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Izneus
 * @date 2020-07-14
 */
@RequiredArgsConstructor
@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
//    StringRedisTemplate
    /**
     * 默认过期时长，单位：秒
     */
//    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
//    public final static long NOT_EXPIRE = -1;

    /**
     * 写redis，key不宜过长影响性能，也不宜过短影响可读性，
     * 建议 object:id 的形式，:分隔，-和.用来连接词语或者domain，
     * 形如 user:1000:followers 或 comment:2000:reply.io
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写redis
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间，默认单位秒
     */
    public void set(String key, Object value, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 写redis
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     */
    public void set(String key, Object value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    /**
     * 读redis
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return StringUtils.hasText(key) ? redisTemplate.opsForValue().get(key) : null;
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key      键
     * @param time     时间
     * @param timeUnit 单位
     */
    public void expire(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 原子加1
     *
     * @param key 键
     * @return ？
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

}
