package com.william.algorithm.concrete_practice;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Package com.william.algorithm.concrete_practice
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/11 19:35
 * @Version V1.0
 */
public class LockSupportTest {

    static Lock lock = new ReentrantLock();

    static int value = 0;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Lock readLock = readWriteLock.readLock();

    static Lock writeLock = readWriteLock.writeLock();


    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        /**
         * 18 + 2 = 20秒才能结束
         */
//        Runnable readR = () -> read(lock);
//        Runnable writeR = () -> write(lock, new Random().nextInt());

        /**
         * 这种方式大大提高了效率，刚刚2秒多一点
         * 1. read很快
         * 2. write需要 2秒
         */
        Runnable readR = () -> read(readLock);
        Runnable writeR = () -> write(writeLock, new Random().nextInt());

        for (int i = 0; i < 18; i++) {
            new Thread(readR).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(writeR).start();
        }
    }

    public static class PhaserTest {

        static MarriagePhaser phaser = new MarriagePhaser();

        public static void main(String[] args) {

            phaser.bulkRegister(7);

            for (int i = 0; i < 5; i++) {
                new Thread(new Person(phaser, "p_" + i)).start();
            }

            new Thread(new Person(phaser, "新娘")).start();
            new Thread(new Person(phaser, "新郎")).start();
        }
    }

    public static class MarriagePhaser extends Phaser {


        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人都到齐了! " + registeredParties);
                    return false;
                case 1:
                    System.out.println("所有人都到吃完了! " + registeredParties);
                    return false;
                case 2:
                    System.out.println("所有人都到离开了! " + registeredParties);
                    return false;
                case 3:
                    System.out.println("婚礼结束! " + registeredParties);
                    return false;
                default:
                    return true;
            }
        }
    }

    public static class Person implements Runnable {
        MarriagePhaser phaser;
        String name;

        public Person(MarriagePhaser phaser, String name) {
            this.phaser = phaser;
            this.name = name;
        }

        public void arrive() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 到达现场! \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 吃完! \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 离开! \n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void hug() {
            if("新郎".equals(name) || "新娘".equals(name)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s 洞房! \n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }
}
