package cn.vskendo.common.interfaces;

/**
 * RedisCacheAble
 *
 * @author vskendo
 * @since 2022/8/24
 */
public interface RedisCacheAble<T>{
    Integer getExpiresTime();
    T getValue();
}
