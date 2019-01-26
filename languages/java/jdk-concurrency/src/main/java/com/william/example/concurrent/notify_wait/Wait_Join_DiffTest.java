package com.william.example.concurrent.notify_wait;

/**
 Similarities between wait() and join()
 Before looking at the difference between wait() and join() method of thread class,
 let's see some of the key similarities between them:
 1) Both wait() and join() is use to pause the current thread in Java. In the first case the thread
    which calls the wait() method goes into waiting for the state while in the second case the
    thread which call the join() method goes into waiting for the state until the thread on
    which join has been called finishes its execution.
 2) Both wait() and join() are overloaded in Java. You can find a version of both wait() and join()
    which accepts a timeout parameter.
 3) Both wait() and join() can be interrupted by calling interrupt() method in Java. Which means
    you can handle the interrupt by catching InterrruptedException in Java.
 4) Both wait() and join() are a non-static method and cannot be called from the static context.

 Difference between wait() and join() method of thread
 So far you have learned what is wait() and join() method, why they are used and some similarities
 between them. Now is the time to revise and find out some key differences between them.
 1) First and foremost difference between wait() and join() method is that wait() is declared and
    defined in Object class while join() is defined in the thread class.
 2) Second important difference between wait() and join() is that wait() must be called from
    synchronized context i.e. synchronized method or block otherwise it will throw
    IllegalMonitorStateException but there is no such requirement for calling join() method in Java.
    You can call join() method with and without synchronized context in Java.
 3) Third difference between wait() and join() method is that the thread calls wait() method
    it release any lock held for the object on which wait() is called, but calling join() method
    doesn't release any monitor or lock.
 4) Another difference between them is that wait() is used for inter-thread communication
    while join() is used for adding sequencing between multiple threads e.g. one thread starts
    execution after first thread finishes its execution.
 5) You can awake a thread waiting by calling wait() method of the object class by using notify()
    and notifyAll() method but you can not break the waiting imposed by join without interruption
    or unless the thread on which join is called has finished execution.
 That's all about the difference between wait() and join() method in Java. Use wait() when you want
 inter-thread communication e.g. to solve the producer-consumer problem while use the Thread.join()
 method when you want one thread to start execution only if the first thread finishes its execution.
 */
public class Wait_Join_DiffTest {

    public static void main(String[] args) {
        threadJoin();
    }

    public static void threadJoin() {
        final Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("t2 start to run ...");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 end ...");
            }
        });

        final Thread t3 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("t3 is always running ...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t1 = new Thread(new Runnable() {
            private int counter = 1;

            public void run() {
                while (true) {
                    System.out.println("t1 start to run ...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter ++;
                    try {
                        if(counter == 2) {
                            t2.start();
                            t2.join();
                        } else if(counter > 5) {
                            t3.start();
                            t3.join();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t1 end ...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
