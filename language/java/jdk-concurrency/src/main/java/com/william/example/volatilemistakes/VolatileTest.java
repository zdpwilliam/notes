package com.william.example.volatilemistakes;

public class VolatileTest {
    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted()) {
            final VolatileSample2 s = new VolatileSample2();

            final Thread w = new Thread() {
                public void run() {
                    s.writer();
                }
            };
            final Thread r = new Thread() {
                public void run() {
                    s.reader();
                }
            };
            r.start();
            w.start();

            new Thread() {
                public void run() {
                    try {
                        w.join();
                        r.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (s.result.equals("x=0,v=true")) {
                        System.out.println(this.getName() + " " + s.result);
                        System.exit(0);
                    }
                }
            }.start();

            Thread.yield();
        }
    }
}
