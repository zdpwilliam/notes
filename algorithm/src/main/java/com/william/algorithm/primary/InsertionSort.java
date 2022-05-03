package com.william.algorithm.primary;

/**
 * Created by william.zhang on 2015/8/25.
 */
public class InsertionSort {

    /**
     * 插入排序升序
     * @param sourceArray
     * @return
     */
    public static int[] insertionSortAsc(int[] sourceArray) {
        for(int i = 1; i < sourceArray.length; i ++) {
            int key = sourceArray[i];
            int j = i - 1;
            while (j >= 0 && sourceArray[j] > key) {
                sourceArray[j + 1] = sourceArray[j];
                sourceArray[j] = key;
                j--;
            }
        }
        return sourceArray;
    }

    /**
     * 降序
     * @param sourceArray
     * @return
     */
    public static int[] insertionSortDesc(int[] sourceArray) {
        for(int i = 1; i < sourceArray.length; i ++) {
            int key = sourceArray[i];
            int j = i - 1;
            while (j >= 0 && sourceArray[j] < key) {
                sourceArray[j + 1] = sourceArray[j];
                sourceArray[j] = key;
                j--;
            }
        }
        return sourceArray;
    }

    public static void main(String[] args) {
        int[] array = {54, 11, 3, 22, 6, 24, 23, 54};
        array = insertionSortAsc(array);
        for(int i = 0; i < array.length; i ++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        array = insertionSortDesc(array);
        for(int i = 0; i < array.length; i ++) {
            System.out.print(array[i] + " ");
        }
    }
}
