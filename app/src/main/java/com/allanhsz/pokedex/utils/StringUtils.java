package com.allanhsz.pokedex.utils;

import android.webkit.URLUtil;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value) {
        if (ObjectUtils.isNull(value)) return true;

        return value.trim().isEmpty();
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(Object value) {
        if (ObjectUtils.isNull(value)) return true;

        return isEmpty(value.toString());
    }

    public static String valueOrEmpty(Object value) {
        if (ObjectUtils.isNull(value)) return "";

        return valueOrEmpty(value.toString());
    }

    public static String valueOrEmpty(String value) {
        return isEmpty(value) ? "" : value;
    }

    public static boolean isValidUrl(String url) {
        return isNotEmpty(url) && URLUtil.isValidUrl(url);
    }

}