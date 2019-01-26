package com.william.example.concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 
 * @author zdpwilliam
 *
 */
public class ExecutorServiceExample {

    public static void main(String[] args) {
//      singleThreadExecutorTest();

//      singleThreadScheduledExecutorTest();
    	
//    	completionServiceTest();
    	
    	completionServiceConcurrentCalculator();
    }

    /**
     * SingleThreadExecutor:
     *
     * - 单例线程，任意时间池中只能有一个线程
     * - 用的是和cache池和fixed池相同的底层池，但线程数目是1-1,0秒IDLE（无IDLE）
     */
    public static void singleThreadExecutorTest() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("singleThreadExecutor execute " + Thread.currentThread().getName());
            }
        });

        final StringBuffer strResult = new StringBuffer();
        Future<StringBuffer> strBufferResult = executor.submit(new Runnable() {
            public void run() {
                strResult.append("run submit runnable result");
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("strBufferResult Thread: "  + Thread.currentThread().getName());
            }
        }, strResult);

        try {
            System.out.println("strBufferResult result : " + strBufferResult.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future<String> result = executor.submit(new Callable<String>() {
            public String call() throws Exception {
                return new String("Hello my singleThreadExecutor");
            }
        });

        try {
            System.out.println("Future result: " + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        while(!executor.isTerminated()) {
            executor.shutdown();
        }
        // executor.shutdownNow();
    }

    /**
     *  SingleThreadScheduledExecutor
     *
     *  - 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。
     *  - （注意，如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程会代替它执行后续的任务）。
     *  可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。与其他等效的 newScheduledThreadPool(1) 不同，
     *  可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。
     */
    public static void singleThreadScheduledExecutorTest() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        long start = System.currentTimeMillis();

        executor.execute(new Runnable() {
            public void run() {
                System.out.println("singleThreadScheduledExecutorTest execute " + Thread.currentThread().getName());
            }
        });

        executor.schedule(new Runnable() {
            public void run() {
                System.out.println("ScheduledExecutorService execute " + Thread.currentThread().getName());
            }
        }, 5000, TimeUnit.MILLISECONDS);

        while(!executor.isTerminated()) {
            System.out.println("cost time: " + (System.currentTimeMillis() - start));
            executor.shutdown();
        }
    }
    
    /**
     * 
     */
    public static void completionServiceTest() {
    	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    	
    	CompletionService<Long> completionService = new ExecutorCompletionService<Long>(executor);
    	
    	Future<Long> result = completionService.submit(new Callable<Long>() {
			public Long call() throws Exception {
				return 1000L;
			}
		});
    	
    	try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	
    	executor.shutdown();
    }
    
    public static void completionServiceConcurrentCalculator() {
        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 10, 11 };
//        ConcurrentCalculator calc = new ConcurrentCalculator();
        CompletionConcurrentCalculator calc = new CompletionConcurrentCalculator();
        Long sum = calc.sum(numbers);
        System.out.println(sum);
        calc.close();
    }
    
    static class ConcurrentCalculator {
        private ExecutorService exec;
        private int cpuCoreNumber;
        private List<Future<Long>> tasks = new ArrayList<Future<Long>>();
      
        public ConcurrentCalculator() {
            cpuCoreNumber = Runtime.getRuntime().availableProcessors();
            exec = Executors.newFixedThreadPool(cpuCoreNumber);
        }
        
        public Long sum(final int[] numbers) {
            // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor
            for (int i = 0; i < cpuCoreNumber; i++) {
                int increment = numbers.length / cpuCoreNumber + 1;
                int start = increment * i;
                int end = increment * i + increment;
                if (end > numbers.length)
                    end = numbers.length;
                SumCalculator subCalc = new SumCalculator(numbers, start, end);
                FutureTask<Long> task = new FutureTask<Long>(subCalc);
                tasks.add(task);
                if (!exec.isShutdown()) {
                    exec.submit(task);
                }
            }
            return getResult();
        }
        
        /** 
         * 迭代每个只任务，获得部分和，相加返回 
         * 
         * 	 getResult()方法的实现过程中，迭代了FutureTask的数组，如果任务还没有完成则当前线程会阻塞，
         * 如果我们希望任意字任务完成后就把其结果加到result中，而不用依次等待每个任务完成，可以使CompletionService。
         * 
         * @return 
         */
        public Long getResult() {
            Long result = 0l;
            for (Future<Long> task : tasks) {
                try {
                    // 如果计算未完成则阻塞 
                    Long subSum = task.get();
                    result += subSum;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
      
        public void close() {
            exec.shutdown();
        }
    }
    
    static class CompletionConcurrentCalculator {  
    	private int cpuCoreNumber;
        private ExecutorService exec;
        private CompletionService<Long> completionService;
      
        public CompletionConcurrentCalculator() {
            cpuCoreNumber = Runtime.getRuntime().availableProcessors();
            exec = Executors.newFixedThreadPool(cpuCoreNumber);
            completionService = new ExecutorCompletionService<Long>(exec);
        }
      
        public Long sum(final int[] numbers) {
            // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor  
            for (int i = 0; i < cpuCoreNumber; i++) {
                int increment = numbers.length / cpuCoreNumber + 1;
                int start = increment * i;
                int end = increment * i + increment;
                if (end > numbers.length)
                    end = numbers.length;
                SumCalculator subCalc = new SumCalculator(numbers, start, end);   
                if (!exec.isShutdown()) {  
                    completionService.submit(subCalc);  
                }
            }
            return getResult();
        }
      
        /** 
         * 迭代每个只任务，获得部分和，相加返回 
         * 
         * 	    生产者submit()执行的任务。使用者take()已完成的任务，并按照完成这些任务的顺序处理它们的结果 。
         * 也就是调用CompletionService的take方法是，会返回按完成顺序放回任务的结果，CompletionService
         * 内部维护了一个阻塞队列BlockingQueue，如果没有任务完成，take()方法也会阻塞。
         * 
         * @return
         */
        public Long getResult() {
            Long result = 0l;
            for (int i = 0; i < cpuCoreNumber; i++) {
                try {
                    Long subSum = completionService.take().get();
                    result += subSum;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
      
        public void close() {
            exec.shutdown();
        }
    }
    
    // 内部类  
    static class SumCalculator implements Callable<Long> {
        private int[] numbers;
        private int start;
        private int end;
        
        public SumCalculator(final int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }
        
        public Long call() throws Exception {
            Long sum = 0l;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        }
    }
}
