package com.william.pattern.singlton;

/**
 * Created by william.zhang on 2015/10/29.
 */
public class SingletonLazy {

    private static SingletonLazy instance = null;

    private SingletonLazy() {
    }

    public static synchronized SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
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
    private int singltonName;

    /**
     * ʾ�ⷽ�������ⲿͨ����Щ�������������Ե�ֵ
     * @return ���Ե�ֵ
     */
    public int getSingltonName() {
        return singltonName;
    }
}
