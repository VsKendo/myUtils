package cn.vskendo.utils;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * PojoUtils
 *
 * @author vskendo
 * @since 2022/9/1
 */
public class PojoUtils {
    @SafeVarargs
    public static <T> void copyPropertiesLambda(Object source, Object target, @Nullable SFunction<T, ?>... ignoreProperties) {
        String[] fieldNames = null;
        if (null != ignoreProperties) {
            fieldNames = new String[ignoreProperties.length];
            for (int i = 0; i < ignoreProperties.length; i++) {
                fieldNames[i] = LambdaExtractUtils.getFieldName(ignoreProperties[i]);
            }
        }
        copyProperties(source, target, fieldNames);
    }

    public static void copyProperties(Object source, Object target, @Nullable String... ignoreProperties) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        PojoExtract<Object> sourceExtract = new PojoExtract<>(source);
        PojoExtract<Object> targetExtract = new PojoExtract<>(target);
        Set<String> ignoreList = (ignoreProperties != null ? new HashSet<>(Arrays.asList(ignoreProperties)) : null);
        Map<String, Field> targetFieldMap = targetExtract.getFieldMap();
        for (Field field : sourceExtract.getFieldList()) {
            if (null == ignoreList || ignoreList.contains(field.getName())) continue;
            Object sourceFieldValue = getFieldValue(field, source, true);
            Field targetField = targetFieldMap.get(field.getName());
            if (null == sourceFieldValue || null == targetField) continue;
            setFieldValue(targetField, target, sourceFieldValue, true);
        }
    }

    public static Object getFieldValue(Field field, Object target, boolean force) {
        boolean accessible = field.isAccessible();
        if (force || accessible) {
            try {
                field.setAccessible(true);
                return field.get(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(accessible);
            }
        }
        return null;
    }

    public static void setFieldValue(Field field, Object target, Object value, boolean force) {
        boolean accessible = field.isAccessible();
        if (force || accessible) {
            try {
                field.setAccessible(true);
                field.set(target, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(accessible);
            }
        }
    }

}
