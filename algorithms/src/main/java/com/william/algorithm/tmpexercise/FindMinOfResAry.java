package com.william.algorithm.tmpexercise;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description: 题目来源：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/
 * @Author deepen.zhang
 * @Date 2021/4/12 10:53
 * @Version V1.0
 */
public class FindMinOfResAry {

    public static void main(String[] args) {
        int[] array = new int[] {12, 15, 19, 2, 5, 9, 10};
        System.out.println(findMin(array));
    }

    /**
     * 输入：nums = [3,4,5,1,2]
     * 输出：1
     * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
     *
     * 输入：nums = [4,5,6,7,0,1,2]
     * 输出：0
     * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
     *
     * 输入：nums = [11,13,15,17]
     * 输出：11
     * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
     *
     * 提示：
     *      n == nums.length
     *      1 <= n <= 5000
     *      -5000 <= nums[i] <= 5000
     *      nums 中的所有整数 互不相同
     *      nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
     *
     * 方案： 二分法处理
     */
    public static int findMin(int[] originalAry) {
        /**
         * [12, 15, 19, 2, 5, 9, 10]
         * [12, 15, 19, 2]
         * [19, 2]
         */
        int low = 0;
        int high = originalAry.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;// 1 + ( 6 - 1) / 2 = 3
            if(originalAry[pivot] < originalAry[high]) {
                high = pivot;
            } else {
                low = pivot + 1;
            }
            System.out.println(String.format("low:[%d] -- high:[%d]", low, high));
        }
        return originalAry[low];
    }

}
