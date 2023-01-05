package com.izneus.bonfire.common.util;

import com.izneus.bonfire.config.BonfireConfig;
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
    // todo 这个其实更加应该作为service存在，而不是common工具类，不过我也想不到放在什么地方好

    private final BonfireConfig bonfireConfig;
    private final RedisTemplate<String, Object> redisTemplate;
    // StringRedisTemplate

    // 默认过期时长，单位：秒
    // public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    // 不设置过期时长
    // public final static long NOT_EXPIRE = -1;

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

    /**
     * 删除redis
     *
     * @param key 键
     */
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

    /**
     * 登录界面的验证码redisKey
     *
     * @param captchaId uuid
     * @return 形如 bonfire:captcha:b17f24ff026d40949c85a24f4f375d42
     */
    public String getCaptchaKey(String captchaId) {
        return String.format("%s:captcha:%s", bonfireConfig.getProjectName(), captchaId);
    }

    /**
     * 登陆重试次数redisKey，重试一般指的是密码错误
     *
     * @param username 用户名
     * @return 形如 bonfire:loginRetryCount:admin
     */
    public String getRetryCountKey(String username) {
        return String.format("%s:loginRetryCount:%s", bonfireConfig.getProjectName(), username);
    }

    /**
     * 用户权限redisKey
     *
     * @param userId 用户id
     * @return 形如 bonfire:userId:123456:privilege
     */
    public String getPrivilegeKey(String userId) {
        return String.format("%s:userId:%s:privilege", bonfireConfig.getProjectName(), userId);
    }

}
