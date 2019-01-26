package com.william.algorithm.primary;

import java.util.Arrays;

/**
 * @author zdpwilliam
 *
 */
public class BubbleSort {

	/**
	 * ð����������(ÿ��ð����С����)
     * @param sourceArray
     * @return
     */
    public static int[] bubbleSortAsc(int[] sourceArray) {
    	for (int i = 0; i < sourceArray.length; i++) {
            for (int j = i + 1; j < sourceArray.length; j++) {
                if (sourceArray[i] > sourceArray[j]) {
                    int temp = sourceArray[i];
                    sourceArray[i] = sourceArray[j];
                    sourceArray[j] = temp;
                }
                System.out.println("��" + (i + 1) + " " + j + "�˽���� " + Arrays.toString(sourceArray));
            }
        }
        return sourceArray;
    }
    
    /**
	 * ð��������(ÿ��ð��������)
     * @param sourceArray
     * @return
     */
    public static int[] bubbleSortDesc(int[] sourceArray) {
    	for (int i = 0; i < sourceArray.length; i++) {
            for (int j = i + 1; j < sourceArray.length; j++) {
                if (sourceArray[i] < sourceArray[j]) {
                    int temp = sourceArray[i];
                    sourceArray[i] = sourceArray[j];
                    sourceArray[j] = temp;
                }
                System.out.println("��" + (i + 1) + " " + j + "�˽���� " + Arrays.toString(sourceArray));
            }
        }
        return sourceArray;
    }
    
	
	public static void main(String[] args) {
		int[] array = {54, 11, 3, 22, 6, 24, 23, 54};
        array = bubbleSortAsc(array);
        for(int i = 0; i < array.length; i ++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        array = bubbleSortDesc(array);
        for(int i = 0; i < array.length; i ++) {
            System.out.print(array[i] + " ");
        }
	}

}
