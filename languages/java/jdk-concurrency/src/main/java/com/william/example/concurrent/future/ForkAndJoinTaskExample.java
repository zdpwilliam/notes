package com.william.example.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 	 fork/join框架是ExecutorService接口的一个实现，可以帮助开发人员充分利用多核处理器的优势，
 * 编写出并行执行的程序，提高应用程序的性能；设计的目的是为了处理那些可以被递归拆分的任务。
 * 
 *　	 fork/join框架与其它ExecutorService的实现类相似，会给线程池中的线程分发任务，不同之处在于它使用了工作窃取算法，
 * 所谓工作窃取，指的是对那些处理完自身任务的线程，会从其它线程窃取任务执行。
 * 
 *　	 fork/join框架的核心是ForkJoinPool类，该类继承了AbstractExecutorService类。
 * ForkJoinPool实现了工作窃取算法并且能够执行 ForkJoinTask任务。
 * 
 * 
 * 在使用fork/join框架之前，我们需要先对任务进行分割，任务分割代码应该跟下面的伪代码类似：
 * 
 * 		if (任务足够小){
 * 			直接执行该任务;
 * 		}else{
 * 			 将任务一分为二;
 * 			 执行这两个任务并等待结果;
 * 		}
 * 
 *	 1、我们会在ForkJoinTask的子类中封装以上代码，不过一般我们会使用更加具体的ForkJoinTask类型，
 * 如 RecursiveTask（可以返回一个结果）或RecursiveAction。
 *	 2、当写好ForkJoinTask的子类后，创建该对象，该对象代表了所有需要完成的任务；
 * 然后将这个任务对象传给ForkJoinPool实例的invoke()去执行即可。
 * 
 * @author zdpwilliam
 *
 */
public class ForkAndJoinTaskExample extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	
	// These attributes will determine the block of products this task has to
	// process.
	private List<Product> products;
	private int first;
	private int last;
	// store the increment of the price of the products
	private double increment;

	public ForkAndJoinTaskExample(List<Product> products, int first, int last, double increment) {
		super();
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

	@Override
	protected void compute() {
		if (last - first < 10) {
			updatePrices();
		} else {
			int middle = (first + last) / 2;
			System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
			ForkAndJoinTaskExample t1 = new ForkAndJoinTaskExample(products, first, middle + 1, increment);
			ForkAndJoinTaskExample t2 = new ForkAndJoinTaskExample(products, middle + 1, last, increment);
			invokeAll(t1, t2);
		}
	}

	public static void main(String[] args) {
		ProductListGenerator productListGenerator = new ProductListGenerator();
		
		List<Product> products = productListGenerator.generate(10000);
		
		ForkAndJoinTaskExample task = new ForkAndJoinTaskExample(products, 0, products.size(), 0.2);
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);

		do {
			System.out.printf("Main: Thread Count: %d\n",
					pool.getActiveThreadCount());
			System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());

		pool.shutdown();

		if (task.isCompletedNormally()) {
			System.out.printf("Main: The process has completed normally.\n");
		}

		for (Product product : products) {
			if (product.getPrice() != 12) {
				System.out.printf("Product %s: %f\n", product.getName(),
						product.getPrice());
			}
		}
		
		System.out.println("Main: End of the program.\n");
	}

	static class Product {
		private String name;
		private double price;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
	}

	static class ProductListGenerator {
		public List<Product> generate(int size) {
			List<Product> list = new ArrayList<Product>();
			for (int i = 0; i < size; i++) {
				Product product = new Product();
				product.setName("Product" + i);
				product.setPrice(10);
				list.add(product);
			}
			return list;
		}
	}
}