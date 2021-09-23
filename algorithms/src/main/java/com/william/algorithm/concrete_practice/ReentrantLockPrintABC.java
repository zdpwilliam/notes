package com.william.algorithm.concrete_practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package com.william.algorithm.concrete_practice
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/23 14:46
 * @Version V1.0
 */
public class ReentrantLockPrintABC {

    private volatile int a = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printABC() {
        new Thread(() -> {
            try {
                lock.lock();
                while (a != 1) {
                    c1.await();
                }
                System.out.println(1);
                a = 2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                while (a != 2) {
                    c2.await();
                }
                System.out.println(2);
                a = 3;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        new Thread(() -> {
            try {
                lock.lock();
                while (a != 3) {
                    c3.await();
                }
                System.out.println(3);
                a = 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }

    public static void main(String[] args) {
        ReentrantLockPrintABC test = new ReentrantLockPrintABC();
        while (true) {
            test.printABC();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
