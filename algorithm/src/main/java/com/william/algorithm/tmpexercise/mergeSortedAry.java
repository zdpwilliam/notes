package com.william.algorithm.tmpexercise;

import java.util.Arrays;

/**
 * @Package com.william.algorithm
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/15 15:08
 * @Version V1.0
 */
public class mergeSortedAry {

    // 函数输入数组，降序：[81, 54, 32, 1], 生序: [3, 5, 12, 31]
    // 输出一个降序数组，包括两个数组的所有数据
    public static void main(String[] args) {
        int[] descAry = new int[] {81, 54, 43, 32, 21, 12, 1};
        int[] ascAry = new int[] {3, 5, 12, 21, 31};
        System.out.println(Arrays.toString(mergeAry(descAry, ascAry)));
    }

    public static int[] mergeAry(int[] descAry, int[] ascAry) {
        int[] mergedAry = new int[descAry.length + ascAry.length];
        int descIndex = 0, ascIndex = ascAry.length - 1, mergeIndex = 0;
        while (ascIndex > 0 && descIndex < descAry.length) {
            int d = descAry[descIndex], a = ascAry[ascIndex];
            if(a > d) {
                mergedAry[mergeIndex] = a;
                ascIndex --;
            } else {
                mergedAry[mergeIndex] = d;
                descIndex ++;
            }
            mergeIndex ++;
        }
        while (ascIndex > 0) {
            mergedAry[mergeIndex ++] = ascAry[ascIndex ++];
        }
        while (descIndex < descAry.length) {
            mergedAry[mergeIndex ++] = descAry[descIndex ++];
        }
        return mergedAry;
    }

}
