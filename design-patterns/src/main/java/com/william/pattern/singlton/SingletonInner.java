package com.william.pattern.singlton;

/**
 * Created by william.zhang on 2015/10/29.
 */
public class SingletonInner {

	/** 
     * �༶���ڲ��࣬Ҳ���Ǿ�̬�ĳ�Աʽ�ڲ��࣬���ڲ����ʵ�����ⲿ���ʵ�� 
     * û�а󶨹�ϵ������ֻ�б����õ��Ż�װ�أ��Ӷ�ʵ�����ӳټ��� 
     */  
    private static class SingletonHolder{
        /**
         * ��̬��ʼ��������JVM����֤�̰߳�ȫ 
         */
        private static SingletonInner instance = new SingletonInner();
    }
    /** 
     * ˽�л����췽�� 
     */  
    private SingletonInner(){
    }
    
    public static  SingletonInner getInstance(){  
        return SingletonHolder.instance;  
    }  
}
