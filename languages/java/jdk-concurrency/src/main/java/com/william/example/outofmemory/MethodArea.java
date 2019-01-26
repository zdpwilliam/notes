package com.william.example.outofmemory;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  VM Args: -XX:PermSize=5M -XX:MaxPermSize=5M
 *  借助Cglib库模拟的方法区溢出，常见的还有：jsp 基于osgi的应用等等
 *      1.8以后此参数已废弃，所以demo不能用了
 */
public class MethodArea {

    public static void main(final String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject {

    }
}
