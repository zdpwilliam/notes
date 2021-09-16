package com.william.algorithm.inner_jdk;

/**
 * @Package com.william.algorithm.inner_jdk
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/5 10:42
 * @Version V1.0
 */
public class ThreadTest {

    private static int counter = 0;

    public static void main(String[] args) {
        System.out.println("main start");
        new ProblemThread().start();
        new BusinessThread().start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        System.out.println("main end");
    }

    public static class ProblemThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                for (int j = 0; j < 1000; j++) {
                    counter += j;
                    counter += i;
                }
            }
            method();
            System.out.println("ProblemThread running ... ");
        }
    }

    public static void method() {
        
    }

    public static class BusinessThread extends Thread {

        @Override
        public void run() {
            System.out.println("BusinessThread running ...");
        }
    }
}
