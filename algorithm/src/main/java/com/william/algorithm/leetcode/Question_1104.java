package com.william.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Package com.william.algorithm.leetcode
 * @Description: https://leetcode-cn.com/problems/path-in-zigzag-labelled-binary-tree/
 * @Author deepen.zhang
 * @Date 2021/7/30 11:24
 * @Version V1.0
 */
public class Question_1104 {

    /**
     *
     * @param label
     * @return
     */
    public static List<Integer> pathInZigZagTree(int label) {
        int row = 1, rowStart = 1;
        while (rowStart * 2 <= label) {
            row++;
            rowStart *= 2;
        }
        System.out.println(String.format("row: %d , rowStart: %d , label: %d", row, rowStart, label));
        if (row % 2 == 0) {
            label = getReverse(label, row);
        }
        System.out.println(String.format("row: %d , rowStart: %d , label-reverse: %d", row, rowStart, label));
        List<Integer> path = new ArrayList<>();
        while (row > 0) {
            if (row % 2 == 0) {
                path.add(getReverse(label, row));
            } else {
                path.add(label);
            }
            System.out.println(String.format("path: %s", path));
            row--;
            label >>= 1;
        }
        Collections.reverse(path);
        return path;
    }

    public static int getReverse(int label, int row) {
        return (1 << row - 1) + (1 << row) - 1 - label;
    }

    public static void main(String[] args) {
        /**
         * 第一个思路是：
         * 1. 整理二叉树
         * 2. 遍历查值
         * 发现不好走，走不通，因为完全没用任何完全二叉树的性质
         *
         * 于是找第二个思路:
         * 1. 利用二叉树性质了解到，层数row与标号 label 的关系（ 2 ^ (row-1) <= label <= 2^row - 1 ），
         *    根据给定的label大小，来定位 row的值
         * 2. 找到每个给定的 label 与原有顺序完全二叉树中同一位置对应的标号 label' 的关系
         *    label' = 2 ^ (row-1) + 2^row - 1 - label，确定函数：getReverse
         * 3. 递减 row 的值，将每个 label >>= 1 ，逐层递减至 1 结束
         * 4. 反转 路径大小
         */
        System.out.println(pathInZigZagTree(26));
    }

    public static class Resolution {

        public static void main(String[] args) {
            System.out.println(new Resolution().pathInZigZagTree(26));
        }

        public List<Integer> pathInZigZagTree(int label) {
            int row = 1, rowStart = 1;
            while(rowStart * 2 <= label) {
                row ++;
                rowStart *= 2;
            }
            System.out.println(String.format("row: %d , rowStart: %d , label: %d", row, rowStart, label));
            if(row % 2 == 0) {
                System.out.println(label);
                label = getReverse(label, row);
                System.out.println(label);
            }
            System.out.println(String.format("row: %d , rowStart: %d , label-reverse: %d", row, rowStart, label));
            List<Integer> result = new ArrayList<>();
            while(row > 0) {
                if(row % 2 == 0) {
                    result.add(getReverse(label, row));
                } else {
                    result.add(label);
                }
                System.out.println(String.format("path: %s", result));
                row --;
                label >>= 1;
            }
            System.out.println(String.format("row: %d , rowStart: %d , label-reverse: %d", row, rowStart, label));
            Collections.reverse(result);
            return result;
        }

        public int getReverse(int label, int row) {
            return (1 << row - 1) + (1 << row) - 1 - label;
        }

    }

}
