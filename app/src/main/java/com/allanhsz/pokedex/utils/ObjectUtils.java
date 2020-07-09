package com.allanhsz.pokedex.utils;

public final class ObjectUtils {

    private ObjectUtils() {
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

}
