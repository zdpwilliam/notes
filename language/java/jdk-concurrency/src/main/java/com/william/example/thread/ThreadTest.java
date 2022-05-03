package com.william.example.thread;

/**
 *
 */
public class ThreadTest {

    /**
     * 线程的方法（Method）、属性（Property）：
     * 1）优先级（priority）
     *      每个类都有自己的优先级，一般property用1-10的整数表示，默认优先级是5，优先级最高是10；
     *      优先级高的线程并不一定比优先级低的线程执行的机会高，只是执行的机率高；
     *      默认一个线程的优先级和创建他的线程优先级相同；
     *
     * 2）Thread.sleep()/sleep(long millis)
     *      当前线程睡眠/millis的时间（millis指定睡眠时间是其最小的不执行时间，因为sleep(millis)休眠到达后，
     *     无法保证会被JVM立即调度）；sleep()是一个静态方法(static method) ，所以他不会停止其他的线程也处于休眠状态；
     *     线程sleep()时不会失去拥有的对象锁。
     *
     *     作用：保持对象锁，让出CPU，调用目的是不让当前线程独自霸占该进程所获取的CPU资源，以留一定的时间给其他线程执行的机会；
     *
     * 3）Thread.yield()
     *      让出CPU的使用权，给其他线程执行机会、让同等优先权的线程运行（但并不保证当前线程会被JVM再次调度、使该线程重新进入Running状态），
     *      如果没有同等优先权的线程，那么yield()方法将不会起作用。
     *
     *      作用：保持对象锁，让出CPU，用yield的时候锁并没有被释放。
     *
     * 4）thread.join()
     *      使用该方法的线程会在此之间执行完毕后再往下继续执行。
     *
     * 5）thread.interrupt ()
     *      如何使用？见：ThreadInterruptMethod类
     *
     * 6）object.wait()
     *       当一个线程执行到wait()方法时，他就进入到一个和该对象相关的等待池(Waiting Pool)中，
     *       同时失去了对象的机锁—暂时的，wait后还要返还对象锁。
     *
     *       当前线程必须拥有当前对象的锁，如果当前线程不是此锁的拥有者，会抛出IllegalMonitorStateException异常,
     *       所以wait()必须在synchronized block中调用。
     *
     * 7）object.notify()/notifyAll()
     *      唤醒在当前对象等待池中等待的第一个线程/所有线程。
     *      notify()/notifyAll()也必须拥有相同对象锁，否则也会抛出IllegalMonitorStateException异常。
     *
     * 8）Synchronizing Block
     *       Synchronized Block/方法控制对类成员变量的访问；Java中的每一个对象都有唯一的一个内置的锁，
     *       每个Synchronized Block/方法只有持有调用该方法被锁定对象的锁才可以访问，否则所属线程阻塞；
     *       机锁具有独占性、一旦被一个Thread持有，其他的Thread就不能再拥有（不能访问其他同步方法），
     *       方法一旦执行，就独占该锁，直到从该方法返回时才将锁释放，此后被阻塞的线程方能获得该锁，重新进入可执行状态。
     *
     *  总而言之，只有使用object.wait()才会释放当前线程使用的对象锁，wait后还要获取该对象锁。
     *
     */

    public static void main(String[] args) {

        MyThreadA myThreadA = new MyThreadA();
        myThreadA.start();
        System.out.println("线程myThreadA临时状态： " + myThreadA.getState());
        myThreadA.interrupt();
        System.out.println("线程myThreadA interrupt 后状态： " + myThreadA.getState());
        myThreadA.interrupted();
        System.out.println("线程myThreadA interrupted 后状态： " + myThreadA.getState());

        Thread myThreadB = new Thread(new RunnableB());
        myThreadB.start();
    }

    static class RunnableB implements Runnable {

        public void run() {
            System.out.println("this thread's name is RunnableB");
        }
    }

    static class MyThreadA extends Thread {

        @Override
        public void run() {
            super.run();
            System.out.println("this thread's name is " + this.getName());
        }
    }
}
