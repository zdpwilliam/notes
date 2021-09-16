package com.william.algorithm.concrete_practice;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Package com.william.algorithm.concrete_practice
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/10 14:29
 * @Version V1.0
 */
public class SynchronizedTest {

    public static class Foo {

        private static final AtomicInteger COUNTER = new AtomicInteger(0);
        private static final LongAdder ADDER = new LongAdder();

        /**
         * volatile 关键字区别
         */
        volatile boolean isRunning = true;

        public void m1() {
            COUNTER.compareAndSet(0, 1);
            ADDER.increment();
            System.out.println("m started");
            while (isRunning) {
                System.out.println("running ...");
            }
            System.out.println("m end");
        }

        public synchronized void feed() {
            System.out.println("I am fool, I am feeding !");
        }

        public void eating() {
            synchronized (this) {
                System.out.println("I am fool, I am eating !");
            }
        }

        public static synchronized void feedStatic() {
            System.out.println("I am fool, I am feeding static!");
        }

        public static void eatingStatic() {
            synchronized (Foo.class) {
                System.out.println("I am fool, I am eating static!");
            }
        }
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        new Thread(foo::m1).start();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        foo.isRunning = false;
        System.out.println(String.format("foo.isRunning has turn off: %s", foo.isRunning));

//        foo.eating();
//        foo.feed();
//
//        Foo.feedStatic();
//        Foo.eatingStatic();
    }

}
