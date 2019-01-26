package com.william.algorithm.concrete_practice;
/**
 * Created by william.zhang on 2015/8/25.
 */
public class EnumExample {

    public static void main(String[] args) {
        System.out.print(AlbumCalcDimensionType.classic.equals(AlbumCalcDimensionType.hot));
    }
    
    
    public enum AlbumCalcDimensionType {
        classic(0),		//����
        hot(1),			//���
        recent(2),		//����
        mostplay(3);	//�������

        private int calcDimension;

        private AlbumCalcDimensionType(int calcDimension) {
            this.calcDimension = calcDimension;
        }

        public static AlbumCalcDimensionType getCalcDimensionType(int calcDimension) {
            for(AlbumCalcDimensionType cdType : values()) {
                if (cdType.getCalcDimension() == calcDimension) {
                    return cdType;
                }
            }
            return null;
        }

        public int getCalcDimension() {
            return calcDimension;
        }
    }
}
