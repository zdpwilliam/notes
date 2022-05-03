package com.william.example.threadloacl;

public class SeqA {
    /**
     *  虽然，ThreadLocal让访问某个变量的线程都拥有自己的局部变量，但
     *  当多个线程返回的ThreadLocal引用类型的变量指向同一个对象时（例如：A）
     *  ThreadLocal就失效了。
     *
     *
     */
    private static A a = new A();

    private static ThreadLocal<A> seqA = new ThreadLocal<A>(){
        // 实现initialValue()
        public A initialValue() {
//          return new A();
            return a;
        }
    };

    public static void main(String[] args) {
        final Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    seqA.get().setNumber(seqA.get().getNumber() + 5);
                    System.out.println(Thread.currentThread().getName() + " : "
                            + seqA.get().getNumber());
                }
            }, "Thread-" + i);
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
    }

    static class A {
        private int number = 0;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "A{" +
                    "number=" + number +
                    '}';
        }
    }
}
