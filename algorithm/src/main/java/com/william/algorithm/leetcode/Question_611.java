package com.william.algorithm.leetcode;

import java.util.*;

/**
 * @Package com.william.algorithm.leetcode
 * <p>
 * 判断：有效三角形的个数
 * @Description: https://leetcode-cn.com/problems/valid-triangle-number/
 * @Author deepen.zhang
 * @Date 2021/8/4 19:52
 * @Version V1.0
 */
public class Question_611 {

    public static void main(String[] args) {
        int[] nums = new int[]{ 24, 3, 82, 22, 35, 84, 19 };
        /**
         * 思路一，首先想到：
         * 1. 三角形性质：两边和大于第三边，两边之差小于第三边
         * 2. N个数字选取3个： C n^3 中的子集来找
         * 问题时间复杂度：O(n3)，空间复杂度：O(n3)  ==>  (不符合要求)
         */
//      System.out.println(triangleNumber1(nums));

        /**
         * 思路二，降低了空间复杂度：
         * 1. 排序
         * 2. 比较 max < min + mid
         * 问题时间复杂度：O(n3)，空间复杂度：O(n)
         */
//        System.out.println(triangleNumber2(nums));

        for (int i = 0; i < 1; i++) {
            /**
             * 思路三，降低了空间复杂度：
             * 1. 排序
             * 2. 二分查找
             * 问题时间复杂度：O(n2logN)，空间复杂度：O(n)
             */
            long now1 = System.currentTimeMillis();
            System.out.println(triangleNumber3(nums));
            System.out.println(String.format("cost1: %d ms", System.currentTimeMillis() - now1));;

            /**
             * 思路四，降低了空间复杂度：
             * 1. 排序
             * 2. 双指针（指针j、k）
             * 问题时间复杂度：O(n2logN)，空间复杂度：O(n)
             */
            long now2 = System.currentTimeMillis();
            System.out.println(triangleNumber4(nums));
            System.out.println(String.format("cost2: %d ms", System.currentTimeMillis() - now2));
        }
    }

    private static int triangleNumber4(int[] nums) {
        Arrays.sort(nums);
        int counter = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                int k = i;
                while (k + 1 < nums.length && nums[k + 1] < nums[i] + nums[j]) {
                    System.out.println(String.format("i:[%d], k+1:[%d], j:[%d] --> value: %d, %d, %d", i, k+1, j, nums[i], nums[k+1], nums[j]));
                    k++;
                }
                System.out.println(String.format("i:[%d], k:[%d], j:[%d]", i, k, j));
                counter = counter + Math.max(k - j, 0);
            }
        }
        return counter;
    }

    private static int triangleNumber3(int[] nums) {
        Arrays.sort(nums);
        int counter = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                int left = j + 1;
                int right = nums.length - 1;
                int n = j;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    if(nums[mid] < nums[i] + nums[j]) {
                        n = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                counter = counter + n - j;
            }
        }
        return counter;
    }

    private static int triangleNumber2(int[] nums) {
        Arrays.sort(nums);
        int counter = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            int min = nums[i];
            int mid, max;
            for (int j = i + 1; j < nums.length; j++) {
                mid = nums[j];
                max = min + mid;
                for (int k = j + 1; k < nums.length; k++) {
                    if(nums[k] < max) {
                        counter ++;
                    }
                }
            }
        }
        return counter;
    }

    public static int triangleNumber1(int[] nums) {
        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    tuples.add(new Tuple(nums[i], nums[j], nums[k]));
                }
            }
        }
        List<Tuple> result = new ArrayList<>();
        for (int i = 0; i < tuples.size(); i++) {
            Tuple tuple = tuples.get(i);
            if (isTriangle(tuple)) {
                result.add(tuple);
            }
        }
        return result.size();
    }

    private static boolean isTriangle(Tuple tuple) {
        int max = Math.max((Math.max(tuple.a, tuple.b)), tuple.c);
        int min, mid;
        if (max == tuple.a) {
            if (tuple.b > tuple.c) {
                min = tuple.c;
                mid = tuple.b;
            } else {
                min = tuple.b;
                mid = tuple.c;
            }
        } else if (max == tuple.b) {
            if (tuple.a > tuple.c) {
                min = tuple.c;
                mid = tuple.a;
            } else {
                min = tuple.a;
                mid = tuple.c;
            }
        } else {
            if (tuple.a > tuple.b) {
                min = tuple.b;
                mid = tuple.a;
            } else {
                min = tuple.a;
                mid = tuple.b;
            }
        }
        return ((min + mid) > max) && (max - mid < min);
    }

    public static class Tuple {
        private int a, b, c;

        public Tuple() {
        }

        public Tuple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tuple)) return false;
            Tuple tuple = (Tuple) o;
            return getA() == tuple.getA() && getB() == tuple.getB() && getC() == tuple.getC();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getA(), getB(), getC());
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }

}
