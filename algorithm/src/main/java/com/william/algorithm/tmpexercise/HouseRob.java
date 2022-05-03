package com.william.algorithm.tmpexercise;

import java.util.Arrays;
import java.util.Random;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description: https://leetcode-cn.com/problems/house-robber-ii/
 * @Author deepen.zhang
 * @Date 2021/4/15 11:37
 * @Version V1.0
 */
public class HouseRob {

    public static void main(String[] args) {
//        int[] rooms = randomAry(9);
        int[] rooms = new int[]{1, 3, 1, 3, 100};
        System.out.println(Arrays.toString(rooms));
        System.out.println(rob(rooms));
    }

    private static int[] randomAry(int len) {
        Random random = new Random();
        int[] randomAry = new int[len];
        for (int i = 0; i < len; i++) {
            randomAry[i] = random.nextInt(len);
        }
        return randomAry;
    }

    /**
     * 示例1：
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     *
     * 示例 2：
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     *     偷窃到的最高金额 = 1 + 3 = 4 。
     *
     * 示例 3：
     * 输入：nums = [0]
     * 输出：0
     *
     * 提示：
     *      1 <= nums.length <= 100
     *      0 <= nums[i] <= 1000
     *
     * @param nums
     * @return
     */
    private static int rob(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        if(nums.length == 2 || nums.length == 3) {
            return Math.max(nums[0], nums[1]);
        }
        int maxSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int localMax = nums[i];
            int high = i % 2 == 0 ? (nums.length - 1) : nums.length;
            for (int j = i + 2; j < high; j += 2) {
                localMax = localMax + nums[j];
                System.out.printf("i:%d,  j:%d \n", i, j);
            }
            if(localMax > maxSum) {
                maxSum = localMax;
            }
            System.out.printf("localMax: %d, max: %d \n", localMax, maxSum);
        }
        return maxSum;
    }

}
