package com.william.algorithm.tmpexercise;

import java.util.EnumSet;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/23 13:51
 * @Version V1.0
 */
public class Test {

    public enum Color {
        RED, YELLOW, GREEN, BLUE, BLACK, WHITE
    }

    public static void main(String[] args) {
        EnumSet enumSet = EnumSet.of(Color.BLUE);
        enumSet.add(Color.WHITE);
        System.out.println(enumSet.contains(Color.RED));

        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        try {
            blockingQueue.put(Integer.valueOf(2));
            blockingQueue.take();
            blockingQueue.add(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
