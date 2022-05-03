package com.william.example.classloader;

import java.util.Stack;

/**
 * Created by william on 17-10-14.
 */
public class ClassLoaderTest {

    /**
     * 1.类加载器:
     * 引导类加载器     -启动类加载器，负责加载存放在<JAVA_HOME>/lib 中的库
     *
     * 扩展类加载器     -ExtClassLoader，负责加载放在 <JAVA_HOME/lib/ext 中的库
     * 应用程序类加载器  -AppClassLoader, ClassLoader类中 getSystemClassLoader()返回值
     *                 负责加载用户类 classpath所指定的类库
     */
    public static void main(String[] args) {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();
        Stack<String> loaderTree = new Stack<String>();
        while (loader != null) {
            loaderTree.push(loader.toString());
            loader = loader.getParent();
        }
        String linePrefix = "|";
        while (!loaderTree.empty()) {
            System.out.println(linePrefix + "  " + loaderTree.pop());
            linePrefix += linePrefix;
        }
    }

    /**
     * 2.
     */
    public void testClassIdentity() {

    }

    public class Sample {
        private Sample instance;

        public void setSample(Object instance) {
            this.instance = (Sample) instance;
        }
    }
}
