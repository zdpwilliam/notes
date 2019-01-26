/**
 * Created by william on 2016/11/30.
 */
package com.william.example.concurrent.executor;

/**
 *      Executor接口中之定义了一个方法execute（Runnable command），该方法接收一个Runable实例，
 * 它用来执行一个任务，任务即一个实现了Runnable接口的类。
 *
 *      ExecutorService接口继承自Executor接口，它提供了更丰富的实现多线程的方法，比如，ExecutorService提供了关闭自己的方法，
 * 以及可为跟踪一个或多个异步任务执行状况而生成 Future 的方法。可以调用ExecutorService的shutdown（）方法来平滑地关闭ExecutorService，
 * 调用该方法后，将导致ExecutorService停止接受任何新的任务且等待已经提交的任务执行完成(已经提交的任务会分两类：一类是已经在执行的，
 * 另一类是还没有开始执行的)，当所有已经提交的任务执行完毕后将会关闭ExecutorService。因此我们一般用该接口来实现和管理多线程。
 *
 *      ExecutorService的生命周期包括三种状态：运行、关闭、终止。
 *      创建后便进入运行状态，当调用了shutdown（）方法时，便进入关闭状态，此时意味着ExecutorService不再接受新的任务，
 * 但它还在执行已经提交了的任务，当素有已经提交了的任务执行完后，便到达终止状态。如果不调用shutdown（）方法，ExecutorService会一直处在运行状态，
 * 不断接收新的任务，执行新的任务，服务器端一般不需要关闭它，保持一直运行即可。
 *
 *      Executors提供了一系列工厂方法用于创先线程池，返回的线程池都实现了ExecutorService接口。
 * */