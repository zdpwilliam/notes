package com.william.example.volatilemistakes;

public class VolatileTestDemo {


    public static volatile int race = 0;

    public static void increase() {
        race ++;
    }

    private static final int THREAD_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i=0; i< 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(race);
    }

}
