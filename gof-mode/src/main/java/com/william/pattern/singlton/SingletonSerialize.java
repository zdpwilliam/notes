package com.william.pattern.singlton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * static inner class
 * 虽然保证了单例在多线程并发下的线程安全性，但是在遇到序列化对象时，默认的方式运行得到的结果就是多例的。
 * 
 * @author zdpwilliam
 */
public class SingletonSerialize implements Serializable {

	private static final long serialVersionUID = -4854100535224327722L;
	
    private static class MySingletonHandler{
    	private static SingletonSerialize instance = new SingletonSerialize();
    }
    
    private SingletonSerialize(){
    }
       
    public static SingletonSerialize getInstance() {
        return MySingletonHandler.instance;  
    }
      
    /**
     * 序列化对象的hashCode和反序列化后得到的对象的hashCode值不一样，说明反序列化后返回的对象是重新实例化的，单例被破坏了，why?
     * 解决办法就是在反序列化的过程中使用readResolve()方法
     * 该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉 
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {  
        System.out.println("调用了readResolve方法！");  
        return MySingletonHandler.instance;   
    }
}
