package com.william.algorithm.inner_jdk;

import java.util.concurrent.ConcurrentSkipListMap;

public class SkipListExample {
	/**
	 * ��Ծ��SkipList����
	 * 1.���������ɣ��ǹؼ����������е����ݽṹ��
	 * 2.�����������һ��head����ָ����ߵļ�����ͣ��ײ����ļ��𣬰������е�key��
	 * 3.ÿһ������������ͼ�����Ӽ�������������ģ�
	 * 4.����ؼ��� key�� ����level=i�г��֣���level<=i�������ж�������ùؼ���key��
	 */
	
	/**
	 * ConcurrentHashMap��ȡ�ٶȱ�ConcurrentSkipListMap��ܶ࣬
	 * ��ConcurrentSkipListMap�м���ConcurrentHashMap���ܱ�����ŵ㣺
	 * 1��ConcurrentSkipListMap ��key������ġ�
	 * 2��ConcurrentSkipListMap ֧�ָ��ߵĲ�����ConcurrentSkipListMap �Ĵ�ȡʱ����log��N�������߳��������޹ء�
	 * Ҳ����˵��������һ��������£��������߳�Խ�࣬ConcurrentSkipListMapԽ�����ֳ��������ơ� 
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
