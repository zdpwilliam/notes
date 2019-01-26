package com.william.example.concurrent.notify_wait;

/**
 * Created by william on 17-10-16.
 */
public class NotifyWaitTest {

    public static void main(String[] args) throws InterruptedException {

        /**
         *  java.lang.IllegalMonitorStateException
         */
        /*Object obj = new Object();
        obj.wait();
        obj.notifyAll();*/

        /**
         *  java.lang.IllegalMonitorStateException
         */
        /*Object obj = new Object();
        Object lock = new Object();
        synchronized (lock) {
            obj.wait();
            obj.notifyAll();
        }*/

        /**
         *  利用wait，notify实现的一个生产者、一个消费者和一个单位的缓存的简单模型
         */
        /*QueueBuffer q = new QueueBuffer();
        new Producer(q);
        new Consumer(q);
        System.out.println("Press Control-C to stop.");*/

        final Object lock = new Object();
        Thread thread1 = new Thread(new OutputThread(1,lock));
        Thread thread2 = new Thread(new OutputThread(2, lock));
        thread1.start();
        thread2.start();
    }

    static class QueueBuffer {
        int n;
        boolean valueSet = false;

        synchronized int get() {
            if (!valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            System.out.println("Got: " + n);
            valueSet = false;
            notify();
            return n;
        }

        synchronized void put(int n) {
            if (valueSet)
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            this.n = n;
            valueSet = true;
            System.out.println("Put: " + n);
            notify();
        }
    }

    static class Producer implements Runnable {
        private QueueBuffer q;

        Producer(QueueBuffer q) {
            this.q = q;
            new Thread(this, "Producer").start();
        }

        public void run() {
            int i = 0;
            while (true) {
                q.put(i++);
            }
        }

    }

    static class Consumer implements Runnable {
        private QueueBuffer q;

        Consumer(QueueBuffer q) {
            this.q = q;
            new Thread(this, "Consumer").start();
        }

        public void run() {
            while (true) {
                q.get();
            }
        }
    }

    static class OutputThread implements Runnable {
        private int num;
        private Object lock;

        public OutputThread(int num, Object lock) {
            super();
            this.num = num;
            this.lock = lock;
        }

        public void run() {
            try {
                while(true){
                    synchronized(lock){
                        lock.notifyAll();
                        lock.wait();
                        System.out.println(num);
                    }
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
