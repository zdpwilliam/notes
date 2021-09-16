package com.william.algorithm.primary;

import java.util.Arrays;

/**
 * @Package com.william.algorithm.primary
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/15 07:57
 * @Version V1.0
 */
public class QuickSort {


    public static void main(String[] args) {
        int[] ary = new int[] {
            6, 17, 2, 31, 82, 45, 29
        };
        quickSort(ary);
        System.out.println(Arrays.toString(ary));
    }

    /**
     * 快速排序
     * @param array
     */
    public static void quickSort(int[] array) {
        int len;
        if(array == null || (len = array.length) == 0 || len == 1) {
            return;
        }
        sort(array, 0, len - 1);
    }

    public static void sort(int[] array, int left, int right) {
        if(left > right) {
            return;
        }
        int base = array[left];
        int i = left, j = right;
        while(i != j) {
            while(array[j] >= base && i < j) {
                j--;
            }
            while(array[i] <= base && i < j) {
                i++;
            }
            if(i < j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        array[left] = array[i];
        array[i] = base;
        sort(array, left, i - 1);
        sort(array, i + 1, right);
    }
}
