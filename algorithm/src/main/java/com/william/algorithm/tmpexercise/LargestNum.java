package com.william.algorithm.tmpexercise;

import java.util.Arrays;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description: https://leetcode-cn.com/problems/largest-number/
 * @Author deepen.zhang
 * @Date 2021/4/12 17:31
 * @Version V1.0
 */
public class LargestNum {


    public static void main(String[] args) {
        System.out.println(findMaxNum(new int[]{}));
    }

    /**
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
     *
     * 输入：nums = [10,2]
     * 输出："210"
     *
     * 输入：nums = [3,30,34,5,9]
     * 输出："9534330"
     *
     * 输入：nums = [1]
     * 输出："1"
     *
     * 输入：nums = [10]
     * 输出："10"
     *
     * 提示：
     *  1 <= nums.length <= 100
     *  0 <= nums[i] <= 10^9
     *
     * @param nums
     * @return
     */
    public static String findMaxNum(int[] nums) {
        // nums: [3,30,34,5,9]
        //9 8 7 6 5 4 3 2 1 0
        Integer[] numAry = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numAry[i] = nums[i];
        }
        // 3,30
        Arrays.sort(numAry, (x, y) -> {
            int sx = 10, sy = 10;
            while (sx <= x) {
                sx *= 10;
            }
            while (sy <= y) {
                sy *= 10;
            }
            return (-sy * x - y + sx * y + x);
        });
        if (numAry.length == 0) {
            return "0";
        }
        StringBuilder ret = new StringBuilder();
        for (int num : numAry) {
            ret.append(num);
        }
        return ret.toString();
    }
}
