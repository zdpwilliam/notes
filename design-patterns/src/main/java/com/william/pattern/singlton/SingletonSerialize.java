package com.william.pattern.singlton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * static inner class
 * ��Ȼ��֤�˵����ڶ��̲߳����µ��̰߳�ȫ�ԣ��������������л�����ʱ��Ĭ�ϵķ�ʽ���еõ��Ľ�����Ƕ����ġ�
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
     * ���л������hashCode�ͷ����л���õ��Ķ����hashCodeֵ��һ����˵�������л��󷵻صĶ���������ʵ�����ģ��������ƻ��ˣ�why?
     * ����취�����ڷ����л��Ĺ�����ʹ��readResolve()����
     * �÷����ڷ����л�ʱ�ᱻ���ã��÷������ǽӿڶ���ķ������е��Լ���׳ɵĸо� 
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {  
        System.out.println("������readResolve������");  
        return MySingletonHandler.instance;   
    }
}
