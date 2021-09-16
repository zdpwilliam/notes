package com.william.oom;

/**
 * @Package com.william.oom
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/1 11:19
 * @Version V1.0
 */
public class OOMDemoTest {






    public static class HeapOOM {

        public static void main(String[] args) {

        }

    }

    /** 原因：
     * 无限递归循环调用（最常见原因），要时刻注意代码中是否有了循环调用方法而无法退出的情况
     * 执行了大量方法，导致线程栈空间耗尽
     * 方法内声明了海量的局部变量
     * native 代码有栈上分配的逻辑，并且要求的内存还不小，比如 java.net.SocketInputStream.read0 会在栈上要求分配一个 64KB 的缓存（64位 Linux）
     *
     * 解决方案:
     * 修复引发无限递归调用的异常代码， 通过程序抛出的异常堆栈，找出不断重复的代码行，按图索骥，修复无限递归 Bug
     * 排查是否存在类之间的循环依赖（当两个对象相互引用，在调用toString方法时也会产生这个异常）
     * 通过 JVM 启动参数 -Xss 增加线程栈内存空间， 某些正常使用场景需要执行大量方法或包含大量局部变量，这时可以适当地提高线程栈空间限制
     */
    public static class StackOverflow_01 {

        public static void main(String[] args) {
            javaKeeper();
        }

        private static void javaKeeper() {
            javaKeeper();
        }
    }
}
