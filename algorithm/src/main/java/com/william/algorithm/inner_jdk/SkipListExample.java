package com.william.algorithm.inner_jdk;

import java.util.concurrent.ConcurrentSkipListMap;

public class SkipListExample {
	/**
	 * 跳跃表（SkipList）：
	 * 1.多条链构成，是关键字升序排列的数据结构；
	 * 2.包含多个级别，一个head引用指向最高的级别，最低（底部）的级别，包含所有的key；
	 * 3.每一个级别都是其更低级别的子集，并且是有序的；
	 * 4.如果关键字 key在 级别level=i中出现，则，level<=i的链表中都会包含该关键字key；
	 */
	
	/**
	 * ConcurrentHashMap存取速度比ConcurrentSkipListMap快很多，
	 * 但ConcurrentSkipListMap有几个ConcurrentHashMap不能比拟的优点：
	 * 1、ConcurrentSkipListMap 的key是有序的。
	 * 2、ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap 的存取时间是log（N），和线程数几乎无关。
	 * 也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。 
	 */
	
	private static final ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<Integer, String>();
	
	private static void init() {
		for (int i = 0; i < 20; i++) {
			skipListMap.put(Integer.valueOf(20 - i), "NO." + i);
		}
	}

	public static void main(String[] args) {
		init();
		
		System.out.println(skipListMap);
	}

}
