package cn.vskendo.utils;


import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * CollectionsUtils 工具类，提供常见集合操作
 *
 * @author vskendo
 * @since 2022/10/3
 */
public class CollectionsUtils extends cn.vskendo.utils.CollectionUtils {
    private CollectionsUtils() {
        super();
    }

    // empty collections ------------------------------------------------------- start

    public static <T> List<T> EmptyList() {
        return Collections.emptyList();
    }

    public static <T> Set<T> EmptySet() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> EmptyMap() {
        return Collections.emptyMap();
    }

    // empty collections ------------------------------------------------------- end

    @SafeVarargs
    public static <T> List<T> ArrayList(T... elements) {
        if (elements == null || elements.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.asList(elements);
    }

    @SafeVarargs
    public static <T> Set<T> HashSet(T... elements) {
        if (elements == null || elements.length == 0) {
            return new HashSet<>();
        }
        HashSet<T> set = new HashSet<>(capacity(elements.length));
        Collections.addAll(set, elements);
        return set;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> HashMap(Object... objects) {
        if (objects == null || objects.length == 0) {
            return new HashMap<>();
        }
        int length = objects.length;
        Map<K, V> map = new HashMap<>(capacity(length));
        if ((length & 1) == 1) {
            throw new IllegalArgumentException("objects length must be even");
        }
        for (int i = 0; i < length; i += 2) {
            map.put((K) objects[i], (V) objects[i + 1]);
        }
        return map;
    }

    /**
     * b中的任意元素是否在a中，相当于 a.contains(b)
     * 效率较低，请勿用于大量数据间的对比
     *
     * @return 包含返回true
     */
    public static <T> boolean contains(Collection<T> a, Collection<T> b) {
        // 如果b为空，那么取决于a是否为空，如果大家都是空，则默认包含，因为空集合包含空集合
        if (isEmpty(b)) {
            return isEmpty(a);
        }
        if (isEmpty(a)) {
            return false;
        }
        for (T t : b) {
            if (a.contains(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含
     *
     * @param tKeyGetter 键值提取函数
     * @param hKeyGetter 键值提取函数
     * @return 包含返回true
     */
    public static <T, H> boolean contains(Collection<T> a, Collection<H> b, Function<T, String> tKeyGetter, Function<H, String> hKeyGetter) {
        // 如果b为空，那么取决于a是否为空，如果大家都是空，则默认包含，因为空集合包含空集合
        if (isEmpty(b)) {
            return isEmpty(a);
        }
        if (isEmpty(a)) {
            return false;
        }
        Map<String, T> cache = HashMap();
        for (T t : a) {
            cache.put(tKeyGetter.apply(t), t);
        }
        for (H h : b) {
            if (cache.containsKey(hKeyGetter.apply(h))) {
                return true;
            }
        }
        return false;
    }

}
