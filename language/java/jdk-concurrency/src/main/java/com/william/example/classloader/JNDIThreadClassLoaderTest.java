package com.william.example.classloader;

/**
 *  双亲委派模型的的"破坏"：
 *  JNDI JDBC JCE JAXB JBI等等应用服务
 */
public class JNDIThreadClassLoaderTest {

    public static void main(String[] args) {
        System.out.println("JNDIThreadClassLoaderTest ---- " + JNDIThreadClassLoaderTest.class);

        Thread thread = new Thread();
        System.out.println(thread.getContextClassLoader());
        System.out.println(thread.getContextClassLoader().getParent());
        System.out.println(thread.getClass().getClassLoader());

        Thread thread1 = new Thread();//创建线程时可以设置 classloader 供JNDI中SPI库的加载
        thread1.setContextClassLoader(Thread.class.getClassLoader());
        System.out.println(thread1.getContextClassLoader());
        thread1.setContextClassLoader(JNDIThreadClassLoaderTest.class.getClassLoader());
        System.out.println(thread1.getContextClassLoader());
    }
}
