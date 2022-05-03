package com.william.algorithm.sort;

import java.util.Arrays;

public class BubbleSort {

    /**
     * 冒泡排序正序 非标准版，因为并不满足元素两两比较的冒泡排序思想
     * @param sourceArray
     * @return
     */
    public static int[] bubbleSort0(int[] sourceArray) {
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = i + 1; j < sourceArray.length; j++) {
                if (sourceArray[i] > sourceArray[j]) {
                    CommonTool.swap(sourceArray, i, j);
                }
                System.out.println((i + 1) + "," + j + ": " + Arrays.toString(sourceArray));
            }
        }
        return sourceArray;
    }

    /**
     * 冒泡排序正序 标准版
     * @param sourceArray
     * @return
     */
    public static int[] bubbleSort1(int[] sourceArray) {
        for (int i = 0; i < sourceArray.length - 1; i++) {
            for (int j = sourceArray.length - 2; j >= i; j--) {
                if (sourceArray[j] > sourceArray[j + 1]) {
                    CommonTool.swap(sourceArray, j, j + 1);
                }
                System.out.println((i + 1) + "," + j + ": " + Arrays.toString(sourceArray));
            }
        }
        return sourceArray;
    }

    /**
     * 冒泡排序正序 优化后
     * @param sourceArray
     * @return
     */
    public static int[] bubbleSort2(int[] sourceArray) {
        boolean flag = true;
        for (int i = 0; i < sourceArray.length && flag; i++) {
            flag = false;
            for (int j = sourceArray.length - 2; j >= i; j--) {
                if (sourceArray[j] > sourceArray[j + 1]) {
                    CommonTool.swap(sourceArray, j, j+1);
                    flag = true;
                }
                System.out.println((i + 1) + "," + j + ": " + Arrays.toString(sourceArray));
            }
        }
        return sourceArray;
    }

    public static void main(String[] args) {
        int[] array0 = {54, 11, 3, 22, 6, 24, 23, 54};
        CommonTool.printArray(bubbleSort0(array0));

        int[] array1 = {54, 11, 3, 22, 6, 24, 23, 54};
        CommonTool.printArray(bubbleSort1(array1));

        int[] array2 = {54, 11, 3, 22, 6, 24, 23, 54};
        CommonTool.printArray(bubbleSort2(array2));
    }
}
