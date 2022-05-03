package com.william.example.outofmemory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 *  VM Args: -Xms20m -Xmx20m -XX:MaxDirectMemorySize=10m
 *
 */
public class DirectMemory {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}