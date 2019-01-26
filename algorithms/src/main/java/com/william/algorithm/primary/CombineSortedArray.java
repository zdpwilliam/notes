package com.william.algorithm.primary;

import java.util.Arrays;

public class CombineSortedArray {

	public static int[] combine(int[] firstSAry, int[] secondSAry) {
		int fLength = firstSAry.length;
		int sLength = secondSAry.length;
		if(fLength ==0 && sLength != 0) {
			return secondSAry;
		}
		if(fLength !=0 && sLength == 0) {
			return firstSAry;
		}
		if(fLength ==0 && sLength == 0) {
			return new int[]{};
		}
		int i = 0, j = 0;
		int[] combinedAry = new int[fLength + sLength];
		
		while(i < fLength && j < sLength) {
			if(firstSAry[i] < secondSAry[j]) {
				combinedAry[i + j] = firstSAry[i];
				i++;
			}
			else {
				combinedAry[i + j] = secondSAry[j];
				j++;
			}
		}
		while(i < fLength) {
			combinedAry[i + j] = firstSAry[i];
			i++;
		}
		while(j < sLength) {
			combinedAry[i + j] = secondSAry[j];
			j++;
		}
		return combinedAry;
	}
	
	public static void main(String[] args) {
		int[] saryb = new int[]{3, 8, 13, 34, 34, 41, 89};
		int[] sarya = new int[]{5, 11, 17, 36, 42, 56, 67, 73};
		System.out.println("合并后的有序数组: " + Arrays.toString(combine(sarya, saryb)));
	}

}
