package com.umpaytest.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hu.ChangLiang
 * @date 2024/3/27 9:34
 */
public class CollUtil {

    /**
     * 会对list中的元素进行去重，并转换为逗号分割的字符串。
     * @param list
     * @return
     */
    public static String listLong2StrAndDistinct(List<Long> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .distinct()
                .collect(Collectors.joining(","));
    }

    public static Set<Long> str2LongSet(String str) {
        return str2LongSet(str, ",");
    }

    public static Set<Long> str2LongSet(String str, String split) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(split))
                .filter(StringUtils::isNotEmpty)
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    public static Set<Integer> str2intSet(String str, String split) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(split))
                .filter(StringUtils::isNotEmpty)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public static Set<String> str2StrSet(String str) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptySet();
        }
        return Arrays.stream(str.split(","))
                .filter(StringUtils::isNotEmpty)
                .map(String::trim)
                .collect(Collectors.toSet());
    }


    public static List<Long> str2LongList(String str) {
        return str2LongList(str, ",");
    }

    public static List<Long> str2LongList(String str, String split) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(split))
                .filter(StringUtils::isNotEmpty)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public static List<Integer> str2intList(String str) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(","))
                .filter(StringUtils::isNotEmpty)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }



    public static List<String> str2strList(String str, String split) {
        if (StringUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(split))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    public static List<String> str2strList(String str) {
        return str2strList(str, ",");
    }

    public static String list2StrAndDistinct(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .filter(StringUtils::isNotEmpty)
                .map(String::trim)
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
    }

    public static boolean contains(String str, Object sub, String split) {
        if (StringUtils.isEmpty(str) && Objects.isNull(sub)) {
            return true;
        }
        if (StringUtils.isEmpty(str) || Objects.isNull(sub)) {
            return false;
        }
        return Arrays.stream(str.split(split))
                .collect(Collectors.toSet())
                .contains(String.valueOf(sub));
    }
}
