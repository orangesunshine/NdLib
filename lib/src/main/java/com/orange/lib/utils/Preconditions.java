package com.orange.lib.utils;

public class Preconditions {
    public static void checkNotNull(Object target) {
        if (null == target) throw new NullPointerException("null == target");
    }

    public static void checkNotNull(Object target, String error) {
        if (null == target) throw new NullPointerException(error);
    }
}
