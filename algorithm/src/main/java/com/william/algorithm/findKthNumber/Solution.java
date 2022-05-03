package com.william.algorithm.findKthNumber;

/**
 * @Package com.william.algorithm.findKthNumber
 * @Description:
 * @Author deepen.zhang
 * @Date 2020-04-05 16:33
 * @Version V1.0
 */
public class Solution {

    static int findKthNumber(int n, int k) {
        int cur = 1;
        k -= 1;
        while (k > 0) {
            int step = calculateLongSteps(n, cur, cur + 1);
            if (step <= k) {
                k -= step;
                cur ++;
            } else {
                k = k - 1;
                cur *= 10;
            }
        }
        return cur;
    }

    private static int calculateLongSteps(int n, long n1, long n2) {//警告：这里用int计算时有误差
        int step = 0;
        while (n1 <= n) {
            step += Math.min(n2, n + 1) - n1;
            n1 *= 10;
            n2 *= 10;
        }
        return step;
    }

    private static int calculateIntSteps(int n, int n1, int n2) {
        int step = 0;
        while (n1 <= n) {
            step += Math.min(n2, n + 1) - n1;
            n1 *= 10;
            n2 *= 10;
        }
        return step;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE/2 > 681692778);
        System.out.println(Long.MAX_VALUE/2 > 681692778);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(calculateIntSteps(681692778,351251360, 681692779));
        System.out.println(calculateLongSteps(681692778,351251360L, 681692779L));
        System.out.println(findKthNumber(681692778, 351251360));
    }

}
