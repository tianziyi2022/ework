package com.hebutgo.ework.common.utils;

/**
 * 字符串处理工具
 * @author zuozhiwei
 */
public class StringUtil {

    /**
     * 删除字符串两端的字符
     * @param source    源字符串
     * @param element   要删除的字符
     * @return
     */
    public static String trim(String source, String element) {
        while (source.startsWith(element)) {
            source = source.substring(element.length());
        }
        while (source.endsWith(element)) {
            int end = source.lastIndexOf(element);
            source = source.substring(0,end);
        }
        return source;
    }

    /**
     * 删除字符串右端的字符
     * @param source    源字符串
     * @param element   要删除的字符
     * @return
     */
    public static String rtrim(String source, String element) {
        while (source.endsWith(element)) {
            int end = source.lastIndexOf(element);
            source = source.substring(0,end);
        }
        return source;
    }

    /**
     * 删除字符串左端的字符
     * @param source    源字符串
     * @param element   要删除的字符
     * @return
     */
    public static String ltrim(String source, String element) {
        while (source.startsWith(element)) {
            source = source.substring(element.length());
        }
        return source;
    }

    /**
     * 判断字符中是否包含空字符
     * @param source
     * @return
     */
    public static boolean containEmpty(String source) {
        for (char c: source.toCharArray()) {
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                return true;
            }
        }
        return false;
    }
}
