package com.william.algorithm.sort;

import java.util.Arrays;

public class SelectSort {

    /**
     * 简单选择正序
     * @param sourceArray
     * @return
     */
    public static int[] selectSort(int[] sourceArray) {
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
}
