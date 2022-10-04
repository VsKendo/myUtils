package cn.vskendo.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 可以不继承父类，从而与commons-lang3包解耦
 *
 * @author vskendo
 * @since 2022/10/4
 */
@SuppressWarnings("unchecked")
public class ValueUtils extends ObjectUtils {
    private ValueUtils() {
    }

    /**
     * 如果为空，则返回默认值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 如果为空，则返回默认值，传入判断是否为空的函数
     *
     * @param value        要判断的value
     * @param defaultValue 默认值
     * @param check        校验函数
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue, Function<T, Boolean> check) {
        return check.apply(value) ? value : defaultValue;
    }

    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 得到一个对象的值并设置进去。常用于一个类的字段。
     *
     * @param getter getter
     * @param setter setter
     * @param <T>    泛型
     * @return 最终设置的值
     */
    public static <T> T getAndSet(Supplier<T> getter, Consumer<T> setter) {
        T value = getter.get();
        if (value != null) {
            setter.accept(value);
        }
        return value;
    }

    /**
     * 得到一个对象的值并设置进去。常用于一个类的字段。如果得到的字段为空，则使用默认值。
     *
     * @param getter getter
     * @param setter setter
     * @param <T>    泛型
     * @return 最终设置的值
     */
    public static <T> T getAndSetDefault(Supplier<T> getter, Consumer<T> setter, T defaultValue) {
        T value = nvl(getter.get(), defaultValue);
        setter.accept(value);
        return value;
    }
}
