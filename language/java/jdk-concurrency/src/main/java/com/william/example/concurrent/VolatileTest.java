package com.william.example.concurrent;

import java.util.Arrays;

/**
 * Created by william on 17-9-5.
 */
public class VolatileTest {

    public volatile int[] array;

    public VolatileTest() {
        array = new int[]{1, 2, 3};
    }

    public VolatileTest(int[] array) {
        this.array = array;
    }

    public static void main(String[] args) {
        final VolatileTest volatileTest = new VolatileTest();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Arrays.toString(volatileTest.array));
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    volatileTest.array[0] = 0;
                    System.out.println(Arrays.toString(volatileTest.array));
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    volatileTest.array = new int[]{3, 2, 1};
                    System.out.println(Arrays.toString(volatileTest.array));
                }
            }
        }).start();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
