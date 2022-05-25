/**
 * Created by william on 2016/11/24.
 */
package com.william;

/**     Java中的多线程是一种抢占机制而不是分时机制。抢占机制指的是有多个线程处于可运行状态，但是只允许一个线程在运行，他们通过竞争的方式抢占CPU。
 * 线程的状态（State）：
 *      新生状态（New），当一个线程的实例被创建即使用new关键字和Thread类或其子类创建一个线程对象后，
 *          此时该线程处于新生(new)状态，处于新生状态的线程有自己的内存空间，但该线程并没有运行，
 *          此时线程还不是活着的（not alive）；
 *      就绪状态（Runnable），通过调用线程实例的start()方法来启动线程使线程进入就绪状态(runnable)，
 *          处于就绪状态的线程已经具备了运行条件，但还没有被分配到CPU即不一定会被立即执行，此时处于线程就绪队列，
 *          等待系统为其分配CPCU，等待状态并不是执行状态，此时线程是活着的（alive）；
 *      运行状态（Running）， 一旦获取CPU(被JVM选中)，线程就进入运行(running)状态，线程的run()方法才开始被执行，
 *          在运行状态的线程执行自己的run()方法中的操作，直到调用其他的方法而终止、或者等待某种资源而阻塞、
 *          或者完成任务而死亡，如果在给定的时间片内没有执行结束，就会被系统给换下来回到线程的等待状态，此时线程是活着的（alive）；
 *      阻塞状态（Blocked），通过调用join()、sleep()、wait()或者资源被暂用使线程处于阻塞(blocked)状态，
 *          处于Blocking状态的线程仍然是活着的（alive）；
 *      死亡状态（Dead），当一个线程的run()方法运行完毕或被中断或被异常退出，该线程到达死亡(dead)状态。
 *          此时可能仍然存在一个该Thread的实例对象，当该Thready已经不可能在被作为一个可被独立执行的线程对待了，
 *          线程的独立的call stack已经被dissolved。一旦某一线程进入Dead状态，他就再也不能进入一个独立线程的生命周期了。
 *          对于一个处于Dead状态的线程调用start()方法，会出现一个运行期(runtime exception)的异常，处于Dead状态的线程不是活着的（not alive）。
 *
 *      线程五种状态的转换，可根据上述描述画出。
 */