package com.william.example.thread;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by william on 2016/11/24.
 */
public class ThreadGroupTest {
    /**
     *  Java中使用ThreadGroup来表示线程组，它可以对一批线程进行分类管理。对线程组的控管理，即同时控制线程组里面的这一批线程。
     *
     *  用户创建的所有线程都属于指定线程组，如果没有显示指定属于哪个线程组，那么该线程就属于默认线程组（即main线程组）。
     *
     *  默认情况下，子线程和父线程处于同一个线程组。
     *
     *  只有在创建线程时才能指定其所在的线程组，线程运行中途不能改变它所属的线程组，也就是说线程一旦指定所在的线程组，就直到该线程结束。
     *
     *  线程组与线程之间结构类似于树形的结构。
     *  
     *  	例如：有 5 个随机时间休眠的线程 (例如，模拟搜索)，然后当其中一个完成，就中断其余的。ThreadGroup类储存线程对象和其他有关联
     *  的 ThreadGroup对象，所以它可以访问他们的所有信息 (例如，状态) 和全部成员的操作表现 (例如，中断)。
     * 
     */

	/** 
	 * 	线程默认为前台线程，这意味着任何前台线程在运行都会保持程序存活。
	 * 	后台线程：只要有一个前台线程在运行，应用程序的进程就在运行。如果多个前台线程在运行，而Main（）方法结束了，应用程序的进程就是激活的，直到所有前台线程完成其任务为止。
	 * 	前台线程和后台线程的唯一的区别是— 后台线程不会阻止进程终止。
	 * 
	 * 	前台线程和后台线程适合的场合：
	 *  通常，后台线程非常适合于完成后台任务，应该将被动侦听活动的线程设置为后台线程，
	 *  而将负责发送数据的线程设置为前台线程，这样，在所有的数据发送完毕之前该线程不会被终止。
	 */
    public static void main(String[] args) {
    	ThreadGroup threadGroup = new ThreadGroup("Searcher");
    	Result result = new Result();
    	SearchTask searchTask = new SearchTask(result);
    	
    	for (int i = 0; i < 5; i++) {
    		Thread thread = new Thread(threadGroup, searchTask);
    		thread.start();
    		try {
    			TimeUnit.SECONDS.sleep(1);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	System.out.printf("Number of Threads: %d\n",threadGroup. activeCount());
    	System.out.printf("Information about the Thread Group\n");
    	threadGroup.list();
    	
    	Thread[] threads = new Thread[threadGroup.activeCount()];
    	threadGroup.enumerate(threads);
    	for (int i = 0; i < threadGroup.activeCount(); i++) {
    		System.out.printf("Thread %s: %s\n",threads[i].getName(),threads[i].getState());
    	}
    	
    	waitFinish(threadGroup);
    	threadGroup.interrupt();
    	
        // 获取主线程所在的线程组，这是所有线程默认的线程组
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("主线程组的名字：" + mainGroup.getName());
        System.out.println("主线程组是否是后台线程组：" + mainGroup.isDaemon());

        new MyThread("主线程组的线程").start();

        ThreadGroup tg = new ThreadGroup("新线程组");
        tg.setDaemon(true);
        System.out.println("tg线程组是否是后台线程组：" + tg.isDaemon());

        MyThread tt = new MyThread(tg, "tg组的线程甲");
//        tt.setDaemon(true);或者false 没什么意思？
        System.out.println("tt线程甲是否后台线程：" + tt.isDaemon()); //这个属性无法从线程组内获得，每个线程是独立的。
        tt.start();

        new MyThread(tg , "tg组的线程乙").start();
    }
    
    private static void waitFinish(ThreadGroup threadGroup) {
    	while (threadGroup.activeCount() > 9) {
    		try {
    			TimeUnit.SECONDS.sleep(1);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
	}

	static class SearchTask implements Runnable {

    	private Result result;
    	
		public SearchTask(Result result) {
			super();
			this.result = result;
		}

		public void run() {
			String name = Thread.currentThread().getName();
			System.out.printf("Thread %s: Start\n",name);
			try {
				doTask();
				result.setName(name);
			} catch (InterruptedException e) {
				System.out.printf("Thread %s: Interrupted\n",name);
				return;
			}
			System.out.printf("Thread %s: End\n",name);
		}
		
		private void doTask() throws InterruptedException {
			Random random=new Random((new Date()).getTime());
			int value=(int)(random.nextDouble() * 100);
			System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(), value);
			TimeUnit.SECONDS.sleep(value);
		}

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}
    }
    
    static class Result {
    	private String name;
    	
    	public Result() {
    		super();
    	}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
    }

    static class MyThread extends Thread {
        // 提供指定线程名的构造器
        public MyThread(String name) {
            super(name);
        }
        // 提供指定线程名、线程组的构造器
        public MyThread(ThreadGroup group , String name) {
            super(group, name);
        }
        public void run() {
            for (int i = 0; i < 20 ; i++ ) {
                System.out.println(getName() + " thread variable : " + i);
            }
        }
    }
}
