package com.william.pattern.singlton;


/**
 * ����ʽ����ʵ�ֵ�ʾ�� 
 * @author zdpwilliam
 *
 */
public class SingletonHungry {
	/**
     * ����һ���������洢�����õ���ʵ����ֱ�������ﴴ����ʵ����ֻ�ᴴ��һ�� 
     */  
    private static SingletonHungry instance = new SingletonHungry();  
    /** 
     * ˽�л����췽���������ڲ����ƴ���ʵ������Ŀ 
     */
    private SingletonHungry(){  
        //  
    }  
    /** 
     * ����һ��������Ϊ�ͻ����ṩ��ʵ�� 
     * @return һ��Singleton��ʵ�� 
     */  
    public static SingletonHungry getInstance(){  
        //ֱ��ʹ���Ѿ������õ�ʵ��  
        return instance;  
    }  
    
    
    /** 
     * ʾ�ⷽ���������������Լ��Ĳ��� 
     */  
    public void singletonOperation(){  
        //���ܴ���  
    }
    
    /** 
     * ʾ�����ԣ������������Լ������� 
     */  
    private String singletonData;  
    /** 
     * ʾ�ⷽ�������ⲿͨ����Щ�������������Ե�ֵ 
     * @return ���Ե�ֵ 
     */  
    public String getSingletonData(){  
        return singletonData;  
    }  
}
