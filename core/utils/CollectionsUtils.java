package cn.vskendo.utils;


import java.util.*;

/**
 * CollectionsUtils
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

}
