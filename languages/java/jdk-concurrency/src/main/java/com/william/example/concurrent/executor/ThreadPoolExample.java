package com.william.example.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by william on 2016/11/30.
 */
public class ThreadPoolExample {
    //  IDLE机制：闲置回收机制

    /**
     *  1、newCachedThreadPool(缓存型线程池)
     *  - 先查看池中有没有以前建立的线程，如果有，就 reuse.如果没有，就建一个新的线程加入池中。
     *  - 缓存型池子通常用于执行一些生存期很短的异步型任务。因此在一些面向连接的 daemon型 SERVER中用得不多。
     *      但对于生存期短的异步任务，它是Executor的首选。
     *  - 能reuse的线程，必须是timeout IDLE内的池中线程，缺省 timeout 是60s,超过这个IDLE时长，线程实例将被终止及移出池。
     *
     *  注意，放入CachedThreadPool的线程不必担心其结束，超过TIMEOUT不活动，其会自动被终止。
     */

    /**
     *  2、newFixedThreadPool(固定线程池)
     *  - newFixedThreadPool与cacheThreadPool差不多，也是能reuse就用，但不能随时建新的线程。
     *  - 其独特之处:任意时间点，最多只能有固定数目的活动线程存在，此时如果有新的线程要建立，
     *      只能放在另外的队列中等待，直到当前的线程中某个线程终止直接被移出池子
     * - 和cacheThreadPool不同，FixedThreadPool没有IDLE机制（可能也有，但既然文档没提，肯定非常长，
     *      类似依赖上层的TCP或UDP IDLE机制之类的），所以FixedThreadPool多数针对一些很稳定很固定的正规并发线程，多用于服务器。
     * - 从方法的源代码看，cache池和fixed 池调用的是同一个底层池，只不过参数不同:
            fixed池线程数固定，并且是0秒IDLE（无IDLE）
            cache池线程数支持0-Integer.MAX_VALUE(显然完全没考虑主机的资源承受能力），60秒IDLE
     */

    /**
     *  3、newScheduledThreadPool(调度型线程池)
     *  - 这个池子里的线程可以按schedule依次delay执行，或周期执行。
     *  - 实际上是corePoolSize课设定的ScheduledExecutor。上文说过ScheduledExecutor采用无界等待队列，
     *  	所以maximumPoolSize没有作用。
     */

	/**
	 * 	4、SingleThreadScheduledExecutor
	 * 	- 实际上是个corePoolSize为1的ScheduledExecutor。
	 *  - ScheduleThreadPoolExecutor 是对ThreadPoolExecutor的集成。增加了定时触发线程任务的功能。需要注意  
	 *		从内部实现看， ScheduleThreadPoolExecutor 使用的是  corePoolSize  线程和一个无界队列的固定大小的池，
	 *		所以调整  maximumPoolSize 没有效果。无界队列是一个内部自定义的 DelayedWorkQueue 。 
	 */
	
    /**
     *  5、ThreadPoolExecutor(自定义线程池)
     *  - 一些参数：
     *   corePoolSize：核心运行的poolSize，也就是当超过这个范围的时候，就需要将新的
     *     Runnable放入到等待队列workQueue中了，我们把这些Runnable就叫做要去执行的任务吧。
     *
     *   maximumPoolSize：一般你用不到，当大于了这个值就会将任务由一个丢弃处理机制来处理，
     *       但是当你发生：newFixedThreadPool的时候，corePoolSize和maximumPoolSize是一样的，
     *       而corePoolSize是先执行的，所以他会先被放入等待队列，而不会执行到下面的丢弃处理中。
     *
     *   workQueue：等待队列，当达到corePoolSize的时候，就向该等待队列放入线程信息（默认为 LinkedBlockingQueue），
     *       运行中的线程属性为：workers，为一个HashSet；我们的Runnable内部被包装了一层，后面会看到这部分代码；
     *       这个队列默认是一个无界队列（也可设定一个有界队列），所以在生产者疯狂生产的时候，考虑如何控制的问题。
     *
     *   keepAliveTime：默认都是0，当线程没有任务处理后，保持多长时间，当你使用：newCachedThreadPool()，
     *       它将是60s的时间。这个参数在运行中的线程从workQueue获取任务时，当(poolSize >corePoolSize ||
     *       allowCoreThreadTimeOut)会用到，当然allowCoreThreadTimeOut要设置为true，也会先判定
     *       keepAliveTime是大于0的，不过由于它在corePoolSize上采用了Integer.MAX_VALUE，当遇到系统遇到瞬间冲击，
     *       workers就会迅速膨胀，所以这个地方就不要去设置allowCoreThreadTimeOut=true，否则结果是这些运行中的
     *       线程会持续60s以上；另外，如果corePoolSize的值还没到Integer.MAX_VALUE，当超过那个值以后，
     *       这些运行中的线程，也是丢弃处理的。
     *
     *    threadFactory：是构造Thread的方法，你可以自己去包装和传递，主要实现newThread方法即可；
     *
     *    handler：也就是参数maximumPoolSize达到后丢弃处理的方法，java提供了5种丢弃处理的方法，
     *       当然你也可以自己根据实际情况去重写，主要是要实现接口：RejectedExecutionHandler中的方法：
     *       public void rejectedExecution(Runnabler, ThreadPoolExecutor e) java默认的是使用：AbortPolicy，
     *       他的作用是当出现这中情况的时候会抛出一个异常；
     *
     *    其余的还包含：
     *    1、CallerRunsPolicy：如果发现线程池还在运行，就直接运行这个线程
     *    2、DiscardOldestPolicy：在线程池的等待队列中，将头取出一个抛弃，然后将当前线程放进去。
     *    3、DiscardPolicy：什么也不做
     *    4、AbortPolicy：java默认，抛出一个异常：RejectedExecutionException。
     *
     *  - 会调用其中的：submit方法或execute方法去操作；其实你会发现，submit方法最终会调用execute方法来进行操作，
     *      只是他提供了一个Future来托管返回值的处理而已，当你调用需要有返回值的信息时，你用它来处理是比较好的；
     *      这个Future会包装对Callable信息，并定义一个Sync对象（），当你发生读取返回值的操作的时候，会通过Sync对象进入锁，
     *      直到有返回值的数据通知，具体细节先不要看太多。
     *
     */

    /**
     *  下面说说几种排队的策略：
     *
     *    1、直接提交。缓冲队列采用 SynchronousQueue，它将任务直接交给线程处理而不保持它们。如果不存在可用于立即运行任务的线程
     *      （即线程池中的线程都在工作），则试图把任务加入缓冲队列将会失败，因此会构造一个新的线程来处理新添加的任务，并将其加入到线程池中。
     *       直接提交通常要求无界 maximumPoolSizes（Integer.MAX_VALUE） 以避免拒绝新提交的任务。newCachedThreadPool采用的便是这种策略。
     *    2、无界队列。使用无界队列（典型的便是采用预定义容量的 LinkedBlockingQueue，理论上是该缓冲队列可以对无限多的任务排队）
     *       将导致在所有 corePoolSize 线程都工作的情况下将新任务加入到缓冲队列中。这样，创建的线程就不会超过 corePoolSize，
     *       也因此，maximumPoolSize 的值也就无效了。当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列。
     *       newFixedThreadPool采用的便是这种策略。
     *    3、有界队列。当使用有限的 maximumPoolSizes 时，有界队列（一般缓冲队列使用ArrayBlockingQueue，并制定队列的最大长度）
     *       有助于防止资源耗尽，但是可能较难调整和控制，队列大小和最大池大小需要相互折衷，需要设定合理的参数。
     *
     */

    public static void main(String[] args) {
//      ExecutorService executorService = Executors.newCachedThreadPool();
//      ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService executorService = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executorService.execute(new TestRunnable());
            System.out.println("************* a" + i + " *************");
        }

        executorService.shutdown();
    }

    static class TestRunnable implements Runnable {

        public void run(){
            System.out.println(Thread.currentThread().getName() + " 线程被调用了。");
        }
    }
}