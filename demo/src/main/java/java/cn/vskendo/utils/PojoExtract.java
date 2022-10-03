package cn.vskendo.utils;

import cn.vskendo.utils.lambda.cache.FieldCacheMap;
import cn.vskendo.utils.lambda.support.ReflectionKit;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * PojoExtract
 *
 * @author vskendo
 * @since 2022/8/31
 */
public class PojoExtract<T> {
    private final T original;
    private final Class<?> clazz;
    private final Map<String, Field> fieldMap;

    /**
     * 默认不包含父类的属性
     *
     * @param clazzObject       反射类
     * @param includeSuperClass 是否包含父类的属性
     */
    public PojoExtract(T clazzObject, Object... includeSuperClass) {
        this.original = clazzObject;
        this.clazz = clazzObject.getClass();
        List<Field> fieldList = FieldCacheMap.getFieldList(clazz, includeSuperClass.length > 0);
        this.fieldMap = ReflectionKit.getFieldMap(fieldList);
    }


    /**
     * 获取字段值
     *
     * @param fieldName 字段名称
     * @return 属性值
     */
    public Object getFieldValue(String fieldName) {
        try {
            Field field = this.fieldMap.get(fieldName);
            Assert.notNull(field, String.format("Error: NoSuchField in %s for %s.", clazz.getSimpleName(), fieldName));
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object o = field.get(original);
            field.setAccessible(accessible);
            return o;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Error: Cannot read field in %s.  Cause:", clazz.getSimpleName()));
        }
    }

    public List<Field> getFieldList() {
        return FieldCacheMap.getFieldList(clazz);
    }

    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public T getOriginal() {
        return original;
    }
}
