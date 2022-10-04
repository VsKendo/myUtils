package cn.vskendo.common.utils;

import cn.vskendo.common.core.RedisCache;
import cn.vskendo.common.interfaces.RedisCacheAble;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * RedisValueCache
 *
 * @author vskendo
 * @since 2022/8/24
 */
@Component
public class RedisValueCache {
    @Getter
    private RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 普通缓存获取，使用阿里的 Fast Json 解析，因为在RedisConfig中使用它来序列化。
     *
     * @param key 键
     * @return 值
     */
    public <t> t get(String key, Class<t> clazz) {
        if (key == null) {
            return null;
        } else {
            Object o = this.redisCache.getRedisTemplate().opsForValue().get(key);
            return JSON.parseObject((String) o, clazz);
        }
    }

    public final RedisCache beginSet() {
        return this.redisCache;
    }

    /**
     * 从Redis中拿值并返回，如果没有值，则通过提供的lambda函数获取值，并在缓存后返回
     *
     * @param key           缓存的key
     * @param clazz         缓存的类型
     * @param emergency     是否紧急，如果是紧急，则不会使用缓存，直接调用lambda函数获取值
     * @param valueSupplier 提供缓存值和过期时间的lambda函数
     * @return 值
     */
    public <T> T getRedisValueByKey(String key, boolean emergency, Class<T> clazz, Supplier<? extends RedisCacheAble<T>> valueSupplier) {
        RedisCacheAble<T> supplier = valueSupplier.get();
        return getRedisValueByKey(key, supplier::getExpiresTime, emergency, clazz, supplier::getValue);
    }

    /**
     * 从Redis中拿值并返回，如果没有值，则通过提供的lambda函数获取值，并在缓存后返回
     *
     * @param key           缓存的key
     * @param clazz         缓存的类型
     * @param expiresTime   缓存的时间
     * @param emergency     是否紧急，如果是紧急，则不会使用缓存，直接调用lambda函数获取值
     * @param valueSupplier 更新缓存值的方法
     * @return 值
     */
    public <T> T getRedisValueByKey(String key, Integer expiresTime, boolean emergency, Class<T> clazz, Supplier<T> valueSupplier) {
        T target = null;
        if (!emergency) {
            target = redisCache.getCacheObject(key, clazz);
        }
        if (ObjectUtils.isEmpty(target)) {
            target = valueSupplier.get();
            redisCache.setCacheObject(key, target, expiresTime);
        }
        return target;
    }

    /**
     * 从Redis中拿值并返回，如果没有值，则通过提供的lambda函数获取值，并在缓存后返回
     *
     * @param key                 缓存的key
     * @param clazz               缓存的类型
     * @param expiresTimeSupplier 提供缓存时间的方法
     * @param emergency           是否紧急，如果是紧急，则不会使用缓存，直接调用lambda函数获取值
     * @param valueSupplier       更新缓存值的方法
     * @return 值
     */
    private <T> T getRedisValueByKey(String key, Supplier<Integer> expiresTimeSupplier, boolean emergency, Class<T> clazz, Supplier<T> valueSupplier) {
        T target = null;
        if (!emergency) {
            target = redisCache.getCacheObject(key, clazz);
        }
        if (ObjectUtils.isEmpty(target)) {
            target = valueSupplier.get();
            redisCache.setCacheObject(key, target, expiresTimeSupplier.get());
        }
        return target;
    }

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
