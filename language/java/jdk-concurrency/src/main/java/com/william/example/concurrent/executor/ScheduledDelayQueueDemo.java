package com.william.example.concurrent.executor;

import java.util.concurrent.*;

/**
 * Created by william on 17-8-8.
 */
public class ScheduledDelayQueueDemo {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        executorService.schedule(new Runnable() {
            public void run() {
                System.out.println("I have printed after 5s ...");
            }
        }, 5, TimeUnit.SECONDS);
        executorService.schedule(new Runnable() {
            public void run() {
                System.out.println("I have printed after 10s ...");
            }
        }, 10, TimeUnit.SECONDS);

        final FutureTask<String> stringFuture = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                return "I am from Callable ... I have printed after 10s";
            }
        });
        executorService.schedule(stringFuture, 10, TimeUnit.SECONDS);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("I am waiting for stringFuture ...");
                    System.out.println(stringFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting for stringFuture end ...");
                /*stringFuture.cancel(true);
                System.out.println("5 second later, stringFuture has been canceled ...");*/
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                int start = 1;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(start ++ + "s");
                }
            }
        }).start();
    }
}
