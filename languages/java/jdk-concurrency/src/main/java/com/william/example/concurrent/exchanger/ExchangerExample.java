package com.william.example.concurrent.exchanger;

import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

	//	某大师的观点，行为知之先。
public class ExchangerExample {
	/**
	 * Exchanger是自jdk1.5起开始提供的工具套件，一般用于两个工作线程之间交换数据。在本文中我将采取由浅入深的方式来介绍分析这个工具类。
	 *
	 * Exchanger有几个要点：
	 *		此类提供对外的操作是同步的；
	 * 		用于成对出现的线程之间交换数据；
	 * 		可以视作双向的同步队列；
	 * 		可应用于基因算法、流水线设计等场景。
	 */
	private static volatile boolean isDone = false;

	// 不断的生产递增整数，data从1递增
	static class ExchangerProducer implements Runnable {
		private Exchanger<Integer> exchanger;
		private static int data = 1;

		ExchangerProducer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		}

		public void run() {
			while (!Thread.interrupted() && !isDone) {
				for (int i = 1; i <= 3; i++) {
					try {
						TimeUnit.SECONDS.sleep(1);
						data = i;
						System.out.println("producer before: " + data);
						data = exchanger.exchange(data);
						System.out.println("producer after: " + data);
					} catch (InterruptedException e) {
						System.out.print(e);
					}
				}
				isDone = true;
			}
		}
	}

	// 不断的生成0，将data置零
	static class ExchangerConsumer implements Runnable {
		private Exchanger<Integer> exchanger;
		private static int data = 0;

		ExchangerConsumer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		}

		public void run() {
			while (!Thread.interrupted() && !isDone) {
				data = 0;
				System.out.println("consumer before : " + data);
				try {
					TimeUnit.SECONDS.sleep(1);
					data = exchanger.exchange(data);
				} catch (InterruptedException e) {
					System.out.print(e);
				}
				System.out.println("consumer after : " + data);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<Integer> exchanger = new Exchanger<Integer>();

		ExchangerProducer producer = new ExchangerProducer(exchanger);
		ExchangerConsumer consumer = new ExchangerConsumer(exchanger);

		// exchanger 将 producer 中的data和 consumer中的data 置换。
		exec.execute(producer);
		exec.execute(consumer);
		exec.shutdown();

		try {
			exec.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.print(e);
		}
	}
}
