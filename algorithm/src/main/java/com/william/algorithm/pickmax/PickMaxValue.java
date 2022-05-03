package com.william.algorithm.pickmax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Package com.william.algorithm.pickmax
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/7 14:57
 * @Version V1.0
 */
public class PickMaxValue {

    public static void main(String[] args) {
        int[] cards = new int[] {13, 3, 7, 11, 9, 1, 2, 8};
        System.out.println(printMax(cards));
        System.out.println(maxScore(cards, 4));
    }

    public static int printMax(int[] cards) {
        int head = 0, tail =  Objects.isNull(cards) ? 1 : cards.length - 1;
        int sum = 0;
        while (head < tail) {
            if(cards[head] > cards[tail]) {
                sum += cards[head];
                head ++;
            } else {
                sum += cards[tail];
                tail --;
            }
            if(cards[head] > cards[tail]) {
                head ++;
            } else {
                tail --;
            }
        }
        return sum;
    }

    public static int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        // 滑动窗口大小为 n-k
        int windowSize = n - k;
        // 选前 n-k 个作为初始值
        int sum = 0;
        for (int i = 0; i < windowSize; ++i)     {
            sum += cardPoints[i];
        }
        int minSum = sum;
        for (int i = windowSize; i < n; ++i) {
            // 滑动窗口每向右移动一格，增加从右侧进入窗口的元素值，并减少从左侧离开窗口的元素值
            sum += cardPoints[i] - cardPoints[i - windowSize];
            minSum = Math.min(minSum, sum);
        }
        return Arrays.stream(cardPoints).sum() - minSum;
    }
}
