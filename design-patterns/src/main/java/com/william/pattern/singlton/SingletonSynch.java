package com.william.pattern.singlton;

/**
 * Created by william.zhang on 2015/10/29.
 */
public class SingletonSynch {

	private static SingletonSynch instance = null;
	
    private SingletonSynch(){
    }
    
    /**
     * 老公singleton类 
     * 直到你需要的时候才给你返回一个 
     * 因为懒汉式会有并发的情况，所以加锁 
     */
    public static synchronized SingletonSynch getInstance(){
        if(instance == null){
            instance = new SingletonSynch();
        }
        return instance;
    }
    
    /**
     * singleton的 Double Check Locking 双检查锁机制（推荐）
     */
    public static SingletonSynch getInstanceSecond(){
        if(instance != null){		//懒汉式   
              return instance;
        }else{  
            //创建实例之前可能会有一些准备性的耗时工作   
            synchronized (SingletonSynch.class) {  
                if(instance == null){//二次检查  
                    instance = new SingletonSynch();  
                }  
            }  
        }
        return instance;  
    }  
}
