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
     * 示意方法，单例可以有自己的操作
     */
    public void singletonOperation(){
        //功能处理
    }

    /**
     * 示意属性，单例可以有自己的属性
     */
    private int singltonName;

    /**
     * 示意方法，让外部通过这些方法来访问属性的值
     * @return 属性的值
     */
    public int getSingltonName() {
        return singltonName;
    }
}
