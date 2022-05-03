package com.william.example.volatilemistakes;

public class Sample {
    /*
     * 不管 a,b是否使用volatile 修饰，都会出现 a、b值交换。因为a=b、b=a并不是原子性
     * 的，因为这两条语句都会涉及到使用与赋值两个动作，完全有可能在访问操作后切换到
     * 另一线程，而volatile并不像synchronized那样具有原子特性
     */
    volatile int a = 1;
    volatile int b = 2;

    void hither() {
        a = b;
    }

    synchronized void yon() {
        b = a;
    }

    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted()) {
            final Sample s = new Sample();
            final Thread hither = new Thread() {
                public void run() {
                    s.hither();
                }
            };
            final Thread yon = new Thread() {
                public void run() {
                    s.yon();
                }
            };
            hither.start();
            yon.start();
            new Thread() {
                public void run() {
                    try {
                        hither.join();
                        yon.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (s.a != s.b) {
                        // 某次打印结果Thread-332984: a=2 b=1
                        System.out.println(this.getName() + ": a=" + s.a + " b=" + s.b);
                        System.exit(0);
                    }
                }

            }.start();

            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}