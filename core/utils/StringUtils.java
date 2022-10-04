package cn.vskendo.utils;

import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final String NULLSTR = "";
    private static final char UNDER_LINE = '_';

    public static String trim(String str) {
        return (str == null ? NULLSTR : str.trim());
    }

    public static String toUpperCase(String str) {
        return (str == null ? NULLSTR : str.toUpperCase());
    }

    public static String toLowerCase(String str) {
        return (str == null ? NULLSTR : str.toLowerCase());
    }

    public static List<String> str2List(String str) {
        if (isNotEmpty(str)) {
            return Arrays.asList(str.split("&"));
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static Set<String> str2Set(String str, String sep) {
        return new HashSet<>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    public static List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean isHttp(String link) {
        return StringUtils.startsWithAny(link, "http", "https");
    }

    /**
     * 是否包含字符串
     *
     * @param str     验证字符串
     * @param strings 字符串组
     * @return 包含返回true
     */
    public static boolean containString(String str, boolean caseSensitive, String... strings) {
        if (isEmpty(str)) {
            return CollectionUtils.isEmpty(strings);
        }
        Predicate<String> predicate = caseSensitive ? str::equals : str::equalsIgnoreCase;
        if (CollectionUtils.isNotEmpty(strings)) {
            for (String s : strings) {
                if (predicate.test(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否包含字符串
     *
     * @param str     验证字符串
     * @param strings 字符串组
     * @return 包含返回true
     */
    public static boolean containString(String str, Predicate<String> compare, String... strings) {
        if (isEmpty(str)) {
            return CollectionUtils.isEmpty(strings);
        }
        if (CollectionUtils.isNotEmpty(strings)) {
            for (String s : strings) {
                if (compare.test(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containString(String str, BiPredicate<String, String> compare, String... strings) {
        if (isEmpty(str)) {
            return CollectionUtils.isEmpty(strings);
        }
        if (CollectionUtils.isNotEmpty(strings)) {
            for (String pattern : strings) {
                if (compare.test(str, trim(pattern))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return true if matched
     */
    public static boolean urlMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padL(final Number num, final int size) {
        return padL(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padL(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                for (int i = size - len; i > 0; i--) {
                    sb.append(c);
                }
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            for (int i = size; i > 0; i--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return NULLSTR;
        }
        char[] original = str.toCharArray();
        int length = original.length;
        int index = 0;
        StringBuilder result = new StringBuilder();
        while (index < length) {
            char c = original[index];
            if (Character.isUpperCase(c)) {
                result.append(UNDER_LINE);
                result.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c) || Character.isDigit(c)) {
                result.append(c);
            }
            index++;
        }
        return UNDER_LINE == result.charAt(0) ? result.substring(1) : result.toString();
    }

    /**
     * 将字符串转换为大驼峰式。例如：Hello_World->HelloWorld
     *
     * @param target 下划线方式命名的字符串
     * @return 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。否则返回转换后的驼峰式命名的字符串
     */
    public static String toUpperCamelCase(String target) {
        return convert2CamelCase(target).toString();
    }

    /**
     * 将字符串转换为小驼峰式。 例如：user_name->userName
     *
     * @return 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。否则返回转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(String target) {
        StringBuilder result = convert2CamelCase(target);
        // 首字母小写
        result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        return result.toString();
    }

    private static StringBuilder convert2CamelCase(String target) {
        StringBuilder result = new StringBuilder();
        if (isEmpty(target)) {
            return result;
        }
        String[] camels = target.toLowerCase().split(UNDER_LINE + "");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (isEmpty(camel)) {
                continue;
            }
            result.append(Character.toUpperCase(camel.charAt(0)));
            result.append(toLowerCase(camel.substring(1)));
        }
        return result;
    }
}
