package com.william.algorithm.sort;

import java.util.Arrays;
import java.util.List;

/**
 *  排序常用工具类
 *
 */
public class CommonTool {

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void swap(List<Integer> sourceList, int i, int j) {
        int temp = sourceList.get(i);
        sourceList.add(i, sourceList.get(j));
        sourceList.add(j, temp);
    }

}
