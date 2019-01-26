package com.william.pattern.singlton;

/**
 * Created by william.zhang on 2015/10/29.
 */
public class SingletonSynch {

	private static SingletonSynch instance = null;
	
    private SingletonSynch(){
    }
    
    /**
     * �Ϲ�singleton�� 
     * ֱ������Ҫ��ʱ��Ÿ��㷵��һ�� 
     * ��Ϊ����ʽ���в�������������Լ��� 
     */
    public static synchronized SingletonSynch getInstance(){
        if(instance == null){
            instance = new SingletonSynch();
        }
        return instance;
    }
    
    /**
     * singleton�� Double Check Locking ˫��������ƣ��Ƽ���
     */
    public static SingletonSynch getInstanceSecond(){
        if(instance != null){		//����ʽ   
              return instance;
        }else{  
            //����ʵ��֮ǰ���ܻ���һЩ׼���Եĺ�ʱ����   
            synchronized (SingletonSynch.class) {  
                if(instance == null){//���μ��  
                    instance = new SingletonSynch();  
                }  
            }  
        }
        return instance;  
    }  
}
