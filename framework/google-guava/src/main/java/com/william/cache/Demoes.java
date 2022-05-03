package com.william.cache;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * 通常来说，Guava Cache 适用于：
 *
 * 1、你愿意消耗一些内存空间来提升速度。
 * 2、你预料到某些键会被查询一次以上。
 * 3、缓存中存放的数据总量不会超出内存容量。 （Guava Cache 是单个应用运行时的本地缓存。
 * 它不把数据存放到文件或外部服务器。如果这不符合你的需求，请尝试 Memcached 这类工具）
 * 
 * @author william
 *
 */
public class Demoes {
	private static LoadingCache<String, List<String>> classes = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.build(new CacheLoader<String, List<String>>() {
                public List<String> load(String key) throws Exception {
                    return selectStudnetFromDB(key);
                }
			});
	

	public static void main(String[] args) {
		
	}

	public static List<String> getCacheLoader(String className) {
		//classes.getUnchecked(className);
		//由于throws Exception，所以就不可以调用 getUnchecked(K)
	    try {
			return classes.get(className);
			//从 LoadingCache 查询的正规方式是使用 get(K)方法。这个方法要么返回已经缓存的值，
			//要么使用 CacheLoader 向缓存原子地加载新值。
		} catch (ExecutionException e) {
			throw new RuntimeException("cacheLoader get() occurs error: " + e.getMessage());
		}
	    
	    /**
	     * 		getAll(Iterable<? extends K>)方法用来执行批量查询。默认情况下，对每个不在缓存中的键，
	     * getAll 方法会单独调用 CacheLoader.load 来加载缓存项。如果批量的加载比多个单独加载更高效，
	     * 你可以重载 CacheLoader.loadAll 来利用这一点。getAll(Iterable)的性能也会相应提升。
	     * 
	     * 		注：CacheLoader.loadAll 的实现可以为没有明确请求的键加载缓存值。例如，为某组中的任意键计算值时，
	     * 能够获取该组中的所有键值，loadAll 方法就可以实现为在同一时间获取该组的其他键值。
	     * 		校注：getAll(Iterable<? extends K>)方法会调用 loadAll，但会筛选结果，只会返回请求的键值对。
	     */
	}
	
	/**
	 * 所有类型的 Guava Cache，不管有没有自动加载功能，都支持 get(K, Callable)方法。
	 * 这个方法返回缓存中相应的值，或者用给定的 Callable 运算并把结果加入到缓存中。
	 * 在整个加载方法完成前，缓存项相关的可观察状态都不会更改。
	 * 这个方法简便地实现了模式"如果有缓存则返回；否则运算、缓存、然后返回"。
	 */
	public static List<String> callable(final String className) {
		try {
	        return classes.get(className, new Callable<List<String>>() {
	            public List<String> call() throws Exception {
	            	return doThingsTheHardWay(className);
	            }
	        });
	    } catch (ExecutionException e) {
	    	throw new RuntimeException("cacheLoader callable() occurs error: " + e.getMessage());
	    }
	}
	
	private static List<String> doThingsTheHardWay(String className) {
		List<String> students = selectStudnetFromDB(className);
		System.out.println(students);
		return students;
	}
	
	/**
	 * 显式插入。
	 * @param className
	 */
	public static void cachePut(String className) {
		//使用 cache.put(key, value)方法可以直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值。
		classes.put(className, selectStudnetFromDB(className));
		/**
		 * 		使用 Cache.asMap()视图提供的任何方法也能修改缓存。但请注意，asMap视图的任何方法
		 * 都不能保证缓存项被原子地加载到缓存中。
		 * 
		 * 		进一步说，asMap 视图的原子运算在 Guava Cache 的原子加载范畴之外，所以相比于 
		 * Cache.asMap().putIfAbsent(K, V)，Cache.get(K, Callable) 应该总是优先使用。
		 */
	}
	
	/**
	 * 显式清除。
	 * 任何时候，你都可以显式地清除缓存项，而不是等到它被回收：
	 * 个别清除：Cache.invalidate(key)
	 * 批量清除：Cache.invalidateAll(keys)
	 * 清除所有缓存项：Cache.invalidateAll()
	 * @param className
	 */
	public static void cacheDelete(String className) {
		classes.invalidate(className);
	}
	
	/**
	 * Guava Cache 提供了三种基本的缓存回收方式：
	 * 		基于容量回收、定时回收和基于引用回收。
	 * 
	 * 1、基于容量的回收（size-based eviction）
	 * 	如果要规定缓存项的数目不超过固定值，只需使用 CacheBuilder.maximumSize(long)。缓存将尝试
	 * 回收最近没有使用或总体上很少使用的缓存项。
	 * ———警告：在缓存项的数目达到限定值之前，缓存就可能进行回收操作——通常来说，这种情况发生在缓存项的
	 * 数目逼近限定值时。
	 * 
	 * 	另外，不同的缓存项有不同的“权重”（weights）——例如，如果你的缓存值，占据完全不同的内存空间，
	 * 你可以使用 CacheBuilder.weigher(Weigher)指定一个权重函数，并且用
	 * CacheBuilder.maximumWeight(long)指定最大总重。在权重限定场景中，除了要注意回收也是
	 * 在重量逼近限定值时就进行了，还要知道重量是在缓存创建时计算的，因此要考虑重量计算的复杂度。
	 */
	public static void cacheWeigher(String className) {
		classes = CacheBuilder.newBuilder()
		        .maximumWeight(100000)
		        .weigher(new Weigher<String, List<String>>() {
		            public int weigh(String key, List<String> value) {
		                return value.size();
		            }
		        })
		        .build(new CacheLoader<String, List<String>>() {
	                public List<String> load(String key) { // no checked exception
	                    return selectStudnetFromDB(key);
	                }
		        });
	}
	
	/**
	 * 2、CacheBuilder 提供两种定时回收的方法：
	 * 
	 * expireAfterAccess(long, TimeUnit)：缓存项在给定时间内没有被读/写访问，则回收。
	 * 	请注意这种缓存的回收顺序和基于大小回收一样。
	 * expireAfterWrite(long, TimeUnit)：缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
	 * 	如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
	 * 
	 * 测试定时回收
	 *	对定时回收进行测试时，不一定非得花费两秒钟去测试两秒的过期。你可以使用 Ticker 接口和 
	 * CacheBuilder.ticker(Ticker)方法在缓存中自定义一个时间源，而不是非得用系统时钟。
	 */
	public static void cacheTimedEviction(String className) {
		classes = CacheBuilder.newBuilder()
		        .maximumWeight(100000)
		        .expireAfterAccess(5, TimeUnit.MINUTES)
		        /*.ticker(new Ticker() {
					@Override
					public long read() {
						return 0;
					}
				})*/
		        .build(new CacheLoader<String, List<String>>() {
	                public List<String> load(String key) { // no checked exception
	                    return selectStudnetFromDB(key);
	                }
		        });
	}
	
	/**
	 * 基于引用的回收（Reference-based Eviction）:
	 * 
	 * 通过使用弱引用的键、或弱引用的值、或软引用的值，Guava Cache 可以把缓存设置为允许垃圾回收：
	 * CacheBuilder.weakKeys()：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。
	 * 	因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是 equals 比较键。
	 * CacheBuilder.weakValues()：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。
	 * 	因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是 equals 比较值。
	 * CacheBuilder.softValues()：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少
	 * 	使用的顺序回收。考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（见上文，
	 * 	基于容量回收）。使用软引用值的缓存同样用==而不是 equals比较值。
	 */
	public static void cacheReferenceEviction(String className) {
		classes = CacheBuilder.newBuilder()
		        .maximumWeight(100000)
		        .weakKeys()
		        .softValues()
		        .build(new CacheLoader<String, List<String>>() {
	                public List<String> load(String key) { // no checked exception
	                    return selectStudnetFromDB(key);
	                }
		        });
	}
	/**
	 * 	使用 CacheBuilder 构建的缓存不会"自动"执行清理和回收工作，也不会在某个缓存项过期后马上清理，也没有诸如此类的清理机制。
	 * 相反，它会在写操作时顺带做少量的维护工作，或者偶尔在读操作时做——如果写操作实在太少的话。
	 * 
	 * 	这样做的原因在于：
	 * 	如果要自动地持续清理缓存，就必须有一个线程，这个线程会和用户操作竞争共享锁。此外，某些环境下线程创建可能受限制，
	 * 这样 CacheBuilder 就不可用了。相反，我们把选择权交到你手里。
	 * 	如果你的缓存是高吞吐的，那就无需担心缓存的维护和清理等工作。如果你的 缓存只会偶尔有写操作，
	 * 而你又不想清理工作阻碍了读操作，那么可以创建自己的维护线程，以固定的时间间隔调用 Cache.cleanUp()和
	 * ScheduledExecutorService 可以帮助你很好地实现这样的定时调度。
	 */
	
	/**
	 * 移除监听器
	 * 	通过 CacheBuilder.removalListener(RemovalListener)，你可以声明一个监听器，
	 * 以便缓存项被移除时做一些额外操作。缓存项被移除时，RemovalListener 会获取移除通知
	 * [RemovalNotification]，其中包含移除原因[RemovalCause]、键和值。
	 * 
	 * ——————请注意，RemovalListener 抛出的任何异常都会在记录到日志后被丢弃[swallowed]。
	 * 
	 * ——————警告：默认情况下，监听器方法是在移除缓存时同步调用的。因为缓存的维护和请求响应通常是同时进行的，
	 * 代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。在这种情况下，你可以使用 
	 * RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作。
	 */
	public static void cacheListener() {
		RemovalListener<String, List<String>> removalListener = new RemovalListener<String, List<String>>() {
			public void onRemoval(RemovalNotification<String, List<String>> removal) {
				System.out.println("system has removed a cache pair : " + removal.getKey() + "-" + removal.getValue());
			}
		};
		
		// RemovalListeners.asynchronous(removalListener, Executors.newFixedThreadPool(50));
		classes = CacheBuilder.newBuilder()
			    .expireAfterWrite(2, TimeUnit.MINUTES)
			    .removalListener(removalListener)
			    .build(new CacheLoader<String, List<String>>() {
	                public List<String> load(String key) throws Exception {
	                    return selectStudnetFromDB(key);
	                }
				});
	}

	private static List<String> selectStudnetFromDB(String key) {
		List<String> students = Lists.newArrayList();
		int studentCount = new Random().nextInt(50);
		for (int i = 0; i < studentCount; i++) {
			students.add(key + "_child_" + i);
		}
		return students;
	}
	
	/**
	 * 刷新
	 * 
	 * 	刷新和回收不太一样。正如 LoadingCache.refresh(K)所声明，刷新表示为键加载新值，
	 * 这个过程可以是异步的。在刷新操作进行时，缓存仍然可以向其他线程返回旧值，而不像回收操作，
	 * 读缓存的线程必须等待新值加载完成。
	 *  如果刷新过程抛出异常，缓存将保留旧值，而异常会在记录到日志后被丢弃[swallowed]。
	 *	重载 CacheLoader.reload(K, V)可以扩展刷新时的行为，这个方法允许开发者在计算新值时使用旧的值。
	 *
	 *	 CacheBuilder.refreshAfterWrite(long, TimeUnit)可以为缓存增加自动定时刷新功能。
	 *	和 expireAfterWrite 相反，refreshAfterWrite 通过定时刷新可以让缓存项保持可用，
	 *	但请注意：缓存项只有在被检索时才会真正刷新（如果 CacheLoader.refresh 实现为异步，
	 *	那么检索不会被刷新拖慢）。因此，如果你在缓存上同时声明 expireAfterWrite 和 refreshAfterWrite，
	 *	缓存并不会因为刷新盲目地定时重置，如果缓存项没有被检索，那刷新就不会真的发生，
	 *	缓存项在过期时间后也变得可以回收。
	 *
	 */
	public static void cacheRefresh() {
		classes = CacheBuilder.newBuilder()
		        .maximumSize(1000)
		        .refreshAfterWrite(1, TimeUnit.MINUTES)
		        .build(new CacheLoader<String, List<String>>() {
	                public List<String> load(String key) { // no checked exception
	                    return selectStudnetFromDB(key);
	                }

	                public ListenableFuture<List<String>> reload(final String key, List<String> students) {
	                    if (neverNeedsRefresh(key)) {
	                        return Futures.immediateFuture(students);
	                    }else{
	                        // asynchronous!
	                        ListenableFutureTask<List<String>> task = ListenableFutureTask.create(new Callable<List<String>>() {
	                            public List<String> call() {
	                                return selectStudnetFromDB(key);
	                            }
	                        });
	                        
	                        Executors.newSingleThreadExecutor().execute(task);
	                        return task;
	                    }
	                }
	            });
	}
	
	private static boolean neverNeedsRefresh(String key) {
		// key的判断逻辑
		return false;
	}
	
	/**
	 * 	统计:
		CacheBuilder.recordStats()用来开启 Guava Cache 的统计功能。统计打开后，
	Cache.stats()方法会返回 CacheStats 对象以提供如下统计信息：
		hitRate()：缓存命中率；
		averageLoadPenalty()：加载新值的平均时间，单位为纳秒；
		evictionCount()：缓存项被回收的总数，不包括显式清除。
		此外，还有其他很多统计信息。这些统计信息对于调整缓存设置是至关重要的，在性能要求高的应用
	中我们建议密切关注这些数据。
		
		asMap 视图: 
		asMap 视图提供了缓存的 ConcurrentMap 形式，但 asMap 视图与缓存的交互需要注意：
		cache.asMap()包含当前所有加载到缓存的项。因此相应地，cache.asMap().keySet()包含当前所有已加载键;
	asMap().get(key)实质上等同于 cache.getIfPresent(key)，而且不会引起缓存项的加载。这和 Map 的语义约定一致。
	所有读写操作都会重置相关缓存项的访问时间，包括 Cache.asMap().get(Object)方法和 Cache.asMap().put(K, V)方法，
	但不包括 Cache.asMap().containsKey(Object)方法，也不包括在 Cache.asMap()的集合视图上的操作。
	比如，遍历 Cache.asMap().entrySet()不会重置缓存项的读取时间。
		
		中断:
		缓存加载方法（如 Cache.get）不会抛出 InterruptedException。我们也可以让这些方法支持 InterruptedException，
	但这种支持注定是不完备的，并且会增加所有使用者的成本，而只有少数使用者实际获益。详情请继续阅读。
		
		Cache.get() 请求到未缓存的值时会遇到两种情况：当前线程加载值；或等待另一个正在加载值的线程。
	这两种情况下的中断是不一样的。
		等待另一个正在加载值的线程属于较简单的情况：
			使用可中断的等待就实现了中断支持；
		但当前线程加载值的情况就比较复杂了：因为加载值的 CacheLoader 是由用户提供的，如果它是可中断的，
	那我们也可以实现支持中断，否则我们也无能为力。
		
		如果用户提供的 CacheLoader 是可中断的，为什么不让 Cache.get 也支持中断？从某种意义上说，
	其实是支持的：如果 CacheLoader 抛出 InterruptedException，Cache.get 将立刻返回（就和其他异常情况一样）；
	此外，在加载缓存值的线程中，Cache.get 捕捉到 InterruptedException 后将恢复中断，而其他线程中 InterruptedException
	则被包装成了 ExecutionException。
		原则上，我们可以拆除包装，把 ExecutionException 变为 InterruptedException，
	但这会让所有的 LoadingCache 使用者都要处理中断异常，即使他们提供的 CacheLoader 不是可中断的。
	如果你考虑到所有非加载线程的等待仍可以被中断，这种做法也许是值得的。但许多缓存只在单线程中使用，
	它们的用户仍然必须捕捉不可能抛出的 InterruptedException 异常。即使是那些跨线程共享缓存的用户，
	也只是有时候能中断他们的 get 调用，取决于那个线程先发出请求。
		对于这个决定，我们的指导原则是让缓存始终表现得好像是在当前线程加载值。
		这个原则让使用缓存或每次都计算值可以简单地相互切换。如果老代码（加载值的代码）是不可中断的，
	那么新代码（使用缓存加载值的代码）多半也应该是不可中断的。
		
		如上所述，Guava Cache 在某种意义上支持中断。另一个意义上说，Guava Cache 不支持中断，
	这使得 LoadingCache 成了一个有漏洞的抽象：当加载过程被中断了，就当作其他异常一样处理，
	这在大多数情况下是可以的；但如果多个线程在等待加载同一个缓存项，即使加载线程被中断了，
	它也不应该让其他线程都失败（捕获到包装在 ExecutionException 里的 InterruptedException），
	正确的行为是让剩余的某个线程重试加载。为此，我们记录了一个 bug。然而，与其冒着风险修复这个 bug，
	我们可能会花更多的精力去实现另一个建议 AsyncLoadingCache，这个实现会返回一个有正确中断行为的 
	Future 对象。
	
	*
	*/
}
