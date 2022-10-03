package com.mtrsz.dpms.common.utils.lambda;



import com.mtrsz.dpms.common.utils.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FieldCacheMap
 *
 * @author vskendo
 * @since 2022/9/3
 */
public class FieldCacheMap {
    private static final Map<String, List<Field>> fieldListMap;

    static {
        fieldListMap = new ConcurrentHashMap<>();
    }

    private FieldCacheMap() {
    }

    public static void cache(Class<?> clazz) {
        fieldListMap.put(clazz.getName(), ReflectionKit.getFieldList(clazz, false));
    }

    public static void cache(Class<?> clazz, boolean isIncludeSuperClass) {
        fieldListMap.put(clazz.getName(), ReflectionKit.getFieldList(clazz, isIncludeSuperClass));
    }

    public static List<Field> getFieldList(Class<?> clazz) {
        return CollectionUtils.computeIfAbsent(fieldListMap, clazz.getName(), (key) ->
                ReflectionKit.getFieldList(clazz, false)
        );
    }

    public static List<Field> getFieldList(Class<?> clazz, boolean isIncludeSuperClass) {
        return CollectionUtils.computeIfAbsent(fieldListMap, clazz.getName(), (key) ->
                ReflectionKit.getFieldList(clazz, isIncludeSuperClass)
        );
    }
}
