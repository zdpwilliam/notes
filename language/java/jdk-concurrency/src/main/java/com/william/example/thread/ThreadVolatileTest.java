package com.william.example.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by william on 17-10-11.
 * http://www.importnew.com/16127.html
 * http://www.cnblogs.com/wrencai/p/5704331.html(volatile内存模型转换)
 */
public class ThreadVolatileTest {
    public static volatile int race = 0;

    public static void increase() {
        race ++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(20);
        Thread[] threads = new Thread[THREADS_COUNT];

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                    countDownLatch.countDown();
                }
            });
            threads[i].start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(race);
    }

    /**
     *     这便是因为race++操作不是一个原子操作，导致一些线程对变量race的修改丢失。
     *  若要使用volatale变量，一般要符合以下两种场景：
     *
     *  1、变量的运算结果并不依赖于变量的当前值，或能够保证只有单一的线程修改变量的值。
     *  2、变量不需要与其他的状态变量共同参与不变约束。
     *
     *  volatale关键字作用：
     *  1、变量的修改对所有线程可见
     *  2、可以禁止JIT编译器进行指令重排序优化，例如：
     */
    /**
     * 单例模式例程一
     *
     */
     static class Singleton_1 {
        private static Singleton_1 instance = null;

        private Singleton_1() {
        }

        public static Singleton_1 getInstacne() {
        /*
         * 这种实现进行了两次instance==null的判断，这便是单例模式的双检锁。
         * 第一次检查是说如果对象实例已经被创建了，则直接返回，不需要再进入同步代码。
         * 否则就开始同步线程，进入临界区后，进行的第二次检查是说：
         * 如果被同步的线程有一个创建了对象实例， 其它的线程就不必再创建实例了。
         */
            if (instance == null) {
                synchronized (Singleton_1.class) {
                    if (instance == null) {
                    /*
                     * 仍然存在的问题：下面这句代码并不是一个原子操作，JVM在执行这行代码时，会分解成如下的操作：
                     * 1.给instance分配内存，在栈中分配并初始化为null
                     * 2.调用Singleton_1的构造函数，生成对象实例，在堆中分配
                     * 3.把instance指向在堆中分配的对象
                     * 由于指令重排序优化，执行顺序可能会变成1，3，2，
                     * 那么当一个线程执行完1，3之后，被另一个线程抢占，
                     * 这时instance已经不是null了，就会直接返回。
                     * 然而2还没有执行过，也就是说这个对象实例还没有初始化过。
                     */
                        instance = new Singleton_1();
                    }
                }
            }
            return instance;
        }
    }
    /**
     * 单例模式例程二
     *
     * @author Colin Wang
     *
     */
    static class Singleton_2 {
        /*
         * 为了避免JIT编译器对代码的指令重排序优化，可以使用volatile关键字，
         * 通过这个关键字还可以使该变量不会在多个线程中存在副本，
         * 变量可以看作是直接从主内存中读取，相当于实现了一个轻量级的锁。
         */
        private volatile static Singleton_2 instance = null;

        private Singleton_2() {
        }

        public static Singleton_2 getInstacne() {
            if (instance == null) {
                synchronized (Singleton_2.class) {
                    if (instance == null) {
                        instance = new Singleton_2();
                    }
                }
            }
            return instance;
        }
    }

}
