package com.william.example.volatilemistakes;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class VolatileThreadAutoIncr extends Thread {
    private static int numSeq;
    private CyclicBarrier barrier;

    public VolatileThreadAutoIncr(CyclicBarrier barrier) {
        super();
        this.barrier = barrier;
    }

    public void run() {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        synchronized (VolatileThreadAutoIncr.class) {
            numSeq += 1;
            System.out.println(this.getId() + " auto incr numSeq is " + numSeq);
        }
    }

    public static void main(String[] args) {
        final CyclicBarrier barrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            new VolatileThreadAutoIncr(barrier).start();
            new VolatileThread().start();
        }
    }

    static class VolatileThread extends Thread {
        private static int numSeq;
        private int num;

        public VolatileThread() {
            num = autoIncr();
        }

        private synchronized int autoIncr() {
            return ++ numSeq;
        }

        @Override
        public void run() {
            System.out.println(this.getId() + " num is " + num);
        }
    }
}
