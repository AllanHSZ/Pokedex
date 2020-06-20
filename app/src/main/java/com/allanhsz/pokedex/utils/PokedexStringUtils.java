package com.allanhsz.pokedex.utils;

public final class PokedexStringUtils {

    private PokedexStringUtils() {
    }

    public static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        }

        return value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }

        return isEmpty(value.toString());
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

}
