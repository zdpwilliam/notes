package com.william.example.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 多线程开发可以更好的发挥多核cpu性能，常用的多线程设计模式有：
 * 
 * 	Future
 * 	Master-Worker
 * 	Guard Susperionsion
 * 	不变
 * 	生产者-消费者 模式
 * 
 */

/**
 * 可取消的异步计算：
 * 	利用开始和取消计算的方法、查询计算是否完成的方法和获取计算结果的方法，
 * 此类提供了对 Future 的基本实现。仅在计算完成时才能获取结果；如果计算尚未完成，
 * 则阻塞 get 方法。一旦计算完成，就不能再重新开始或取消计算。
 *
 * 	可使用 FutureTask 包装 Callable 或 Runnable 对象。
 * 因为 FutureTask 实现了 Runnable，所以可将 FutureTask 提交给 Executor 执行。
 * 	除了作为一个独立的类外，此类还提供了 protected 功能，这在创建自定义任务类时可能很有用。
 * 
 * @author william
 *
 */
public class FutureTaskExample {

    public static void main(String[] args) {
        final int number = 10;
        
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("executor : " + Thread.currentThread().getName());

        FutureTask<List<Integer>> futureTask = new FutureTask<List<Integer>>(new Callable<List<Integer>>() {

            public List<Integer> call() throws Exception {
                System.out.println("call() : " + Thread.currentThread().getName());
                List<Integer> target = new ArrayList<Integer>();
                for (int i = 0; i < number; i++) {
                    target.add(i);
                }
                Thread.sleep(2000);
                return target;
            }
        });

        executor.submit(futureTask);
        
        System.out.println("futureTask start !");
        
//        futureTask.cancel(false);
        
        try {
            System.out.println("futureTask result: " + futureTask.get());
        } catch (InterruptedException e ) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
        	executor.shutdown();
        }
        
    }
    
}
