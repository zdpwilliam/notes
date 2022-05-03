package com.william.example.thread;

/**
 * reference: http://www.cnblogs.com/carmanloneliness/p/3516405.html
 *
 * Created by william on 2016/11/28.
 */
public class ThreadInterruptMethod {

    /*
    一：一些概念和重要方法

    　　interrupt status(中断状态)：请记住这个术语，中断机制就是围绕着这个字段来工作的。在Java源码中代表中断状态的字段是：
        private volatile Interruptible blocker;对“Interruptible”这个类不需要深入分析，对于“blocker”变量有以下几个操作。

            　　1.默认blocker=null;　®1
            　　2.调用方法“interrupt0();”将会导致“该线程的中断状态将被设置(JDK文档中术语)”。®2
            　　3.再次调用“interrupt0();”将会导致“其中断状态将被清除(同JDK文档中术语)”®3
            注：这三点很重要，接下来文章中会用来®1®2®3代替。

       明白了第一点来看下文档中对于中断线程相关方法的描述:
       1.public void interrupt();

    　　　　中断线程。如果线程在调用 Object 类的 wait()、wait(long) 或 wait(long, int) 方法，
        或者该类的 join()、join(long)、join(long, int)、sleep(long) 或 sleep(long, int) 方法
    　　过程中受阻，则其中断状态将被清除，它还将收到一个 InterruptedException。　

       2.public static boolean interrupted();

    　　　　测试当前线程是否已经中断。线程的中断状态 由该方法清除。线程中断被忽略，
        因为在中断时不处于活动状态的线程将由此返回 false 的方法反映出来。
        返回：         如果当前线程已经中断，则返回 true；否则返回 false。
        另请参见：     isInterrupted()

    　　3.public boolean isInterrupted();

    　　　　测试线程是否已经中断。线程的中断状态 不受该方法的影响。线程中断被忽略，
        因为在中断时不处于活动状态的线程将由此返回 false 的方法反映出来。

            返回：     如果该线程已经中断，则返回 true；否则返回 false。
            另请参见： interrupted()

    　　<!--来自JDK API文档-->
       以上三段中关于线程的中断状态 由该方法清除的描述，在源码层面就是®3调用。
    二、"interrupted()"和"isInterrupted()"两个方法的相同点和不同点
        相同点都是判断线程的interrupt status是否被设置，若被设置返回true,否则返回false.
        区别有两点：
            1：前者是static方法，调用者是current thread,而后者是普通方法，调用者是this current；
            2：它们其实都调用了Java中的一个native方法isInterrupted(boolean ClearInterrupted)；不同的是前者传入了参数true,后者传入了false。
        意义就是：前者将清除线程的interrupt state(®3),调用后者线程的interrupt state不受影响。
     */

    //example: 这个例子说明了两个问题。
    // 1.调用interrupt()方法并不会中断一个正在运行的线程.
    // 2.若调用sleep()而使线程处于阻塞状态，这时调用interrupt()方法，会抛出InterruptedException,
    // 从而使线程提前结束阻塞状态，退出阻塞代码。

    public static void main(String[] args) {
        ThreadInterruptMethod main = new ThreadInterruptMethod();
        Thread t = new Thread(main.runnable);
        System.out.println("mainmainmain");
        t.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.interrupt();  // 这里的异常并不是sleep()抛出的，而是interrupt()抛出的。
    }

    Runnable runnable = new Runnable() {
        public void run() {
            int i = 0;
            try {
                while (i < 1000) {
                    Thread.sleep(500);          //此时会调用方法®2，设置了Interruptible blocker；变量的值
                    System.out.println(i++);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    /*
    * 三：分析下例子中的中断机制
    *   1.为什么调用interrupt()并不能中断线程？
    *   public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

            synchronized (blockerLock) {
                Interruptible b = blocker;
                if (b != null) {
                    interrupt0();           // Just to set the interrupt flag
                    b.interrupt();
                    return;
                }
            }
            interrupt0();
        }
    *
    *
    *
    *
    *
    *
    *
    *
    *
    *
    * */


}
