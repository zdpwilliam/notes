package com.william.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 可以采用自定义的ThreadFactory工厂，增加对线程创建与销毁等更多的控制
 * @author zdpwilliam
 *
 */
public class ThreadFactoryExample {

	public static void main(String[] args) {
		
		ThreadFactory simpleTdFty = new SimpleThreadFactory();
		simpleTdFty.newThread(new Runnable() {
			public void run() {
				System.out.println("SimpleThreadFactory create thread: " + Thread.currentThread().getName());
			}
		}).start();
		
		ThreadFactory simpleThreadFactory = Executors.defaultThreadFactory();
		simpleThreadFactory.newThread(new Runnable() {
			public void run() {
				System.out.println("Executors.defaultThreadFactory() create thread: " + Thread.currentThread().getName());
			}
		}).start();
		
		
		/*  返回用于创建新线程的线程工厂，这些新线程与当前线程具有相同的权限。此工厂创建具有与 defaultThreadFactory()相同设置的线程，
		 * 新线程的 AccessControlContext 和 contextClassLoader 的其他设置与调用此 privilegedThreadFactory 方法的线程相同。
		 *  可以在 AccessController.doPrivileged(java.security.PrivilegedAction )操作中创建一个新 privilegedThreadFactory，
		 * 设置当前线程的访问控制上下文，以便创建具有该操作中保持的所选权限的线程。 
		 * 
		 *  注意，虽然运行在此类线程中的任务具有与当前线程相同的访问控制和类加载器，但是它们无需具有相同的 ThreadLocal 或 InheritableThreadLocal 值。
		 *  如有必要，使用 ThreadPoolExecutor.beforeExecute(java.lang.Thread, java.lang.Runnable )在 ThreadPoolExecutor 
		 * 子类中运行任何任务前，可以设置或重置线程局部变量的特定值。另外，如果必须初始化 worker 线程，以具有与某些其他指定线程相同的 InheritableThreadLocal
		 * 设置，则可以在线程等待和服务创建请求的环境中创建自定义的 ThreadFactory，而不是继承其值。
		 * 
		 * 
		 * */
		ThreadFactory privilegedThreadFactory = Executors.privilegedThreadFactory();
		System.out.println("main thread privileged: " + Thread.currentThread().getContextClassLoader());
		privilegedThreadFactory.newThread(new Runnable() {
			
			public void run() {
				System.out.println("this thread privileged: " + Thread.currentThread().getContextClassLoader());
			}
		}).start();
		
		// 带异常捕获的自定义线程工厂
		ExecutorService executor = Executors.newSingleThreadExecutor(new LoggerThreadFactory());
		executor.submit(new Runnable() {
			public void run() {
//				someObject.methodThatThrowsRuntimeException();
				System.out.println("someObject.methodThatThrowsRuntimeException()");
			}
		});
	}

	//此接口最简单的实现就是
	static class SimpleThreadFactory implements ThreadFactory {
	   public Thread newThread(Runnable r) {
		   return new Thread(r);
	   }
	}
	
	// 带异常捕获的自定义线程工厂
	static class LoggerThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
		   Thread t = new Thread(r);
		   t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			   
			   public void uncaughtException(Thread t, Throwable e) {
//				   System.out.println(2 / 0);
				   // LoggerFactory.getLogger(t.getName()).error(e.getMessage(), e);
		       }
		   });
		   return t;
		}
	} 
}
