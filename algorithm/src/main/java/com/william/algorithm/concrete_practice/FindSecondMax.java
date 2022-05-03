package com.william.algorithm.concrete_practice;

import java.util.Arrays;

public class FindSecondMax {

	/**
	 * 冒泡排序升序(每次冒出最小的数)
     * @param sourceArray
     * @return
     */
    public static int findSecMax(int[] sourceArray) {
    	int max = sourceArray[0];
    	int seconMax = 0;
    	if(max > sourceArray[1]) {
    		seconMax = sourceArray[1];
    	}
    	else {
    		max = sourceArray[1];
    		seconMax = sourceArray[0];
    	}
    	
    	for (int i = 2; i < sourceArray.length; i++) {
    		if(max > sourceArray[i]) {
    			if(seconMax < sourceArray[i]) {
    				seconMax = sourceArray[i];
    			}
    		}
    		else {
    			seconMax = max;
    			max = sourceArray[i];
    		}
        }
        return seconMax;
    }
    
    /**
	 * 去掉数组中重复的元素
	 * @param sourceArray
	 * @return
	 */
	public static int[] distinct(int[] sourceArray) {
		int length = 1;
		for(int i = 1; i < sourceArray.length; i++) {
			boolean isExist = false;
			for(int j = 0; j < length; j++){
				if(sourceArray[i] == sourceArray[j]) {
					isExist = true;
				}
			}
			if(!isExist){
				sourceArray[length] = sourceArray[i];
				length++;
			}
			isExist = false;
		}
		int[] newArray = new int[length];
		System.arraycopy(sourceArray, 0, newArray, 0, length);
		return newArray;
	}
	
    public static void main(String[] args) {
		int[] array = {54, 11, 3, 22, 6, 24, 23, 54};
		
		System.out.println("去重后数组： " + Arrays.toString(distinct(array)));
        System.out.print("第二大的数是： " + findSecMax(array));
	}

}
