package com.william.example.volatilemistakes;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class VolatileFlag {
    private volatile boolean flag = false;

    public void shutdown() { flag = true; }

    public void doWork() {
        while (!flag) {
            System.out.println("flag is " + flag);
        }
    }

    public static void main(String[] args) {
        final VolatileFlag flag = new VolatileFlag();
        final CyclicBarrier barrier = new CyclicBarrier(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    flag.doWork();
                }
            }).start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag.shutdown();
        System.out.println("has been shut down!");
    }
}
