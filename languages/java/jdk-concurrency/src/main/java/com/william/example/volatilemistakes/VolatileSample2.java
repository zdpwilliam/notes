package com.william.example.volatilemistakes;

public class VolatileSample2 {
    int x = 0;
    volatile boolean v = false;
    String result;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        /*
         * 如果是 result="x="+x+",v="+v;，则快就会出现 x=0,v=true 这样的结果，因为这
         * 条语 句完全可能先访问x后，另外线程再执行writer方法，待writer方法执行完成后
         * ，再接着访问v，此时就会出现 x=0,v=true 的结果；
         *
         * 但如果是result="v="+v+",x="+x;，则要想出现 v=true,x=0 的结果，则一定要等
         * writer方法执行完并写回到主内存后再执行reader方法，由于v声明的是volatile变
         * 量，volatile变量会禁止reorder任意两个者volatile变量，并且同时严格限制
         * reorder volatile变量周围 的非 volatile变量，所以由于x 比v前赋值，则写回主
         * 存时也会一定按照此顺序，所以当v为true时，则主存中的x肯定是42，绝不会是0（
         * 这里不要注意的是，volatile的变量也会在读取主内存时严格按照程序的顺序执行，
         * 所以这里根本不会先访问x再v的可能，如果这那样，则也会出现v=true,x=0的结果）。
         *
         * 这里只将x声明成volatile其结果也是一样的。当然如果两个都声明成volatile时，
         * 会更安全，因为这里会完全“禁止”重排，而一个的话只是“严格限制”而已，可能还是
         * 不会很安全，所以一般 两个都设置为最安全。
         *
         * 当x,v都是volatile时， result="x="+x+",v="+v;执行的结果还是有可能为
         * x=0,v=true， 因为volatile只是保证了可见性与顺序性两个特点为，但并不能保证
         * 原子性。此种情况下要得到 x=0,v=true，只需reader方法先执行，等访问x完后而v
         * 还未访问时，开始调试writer方法， 待writer整个方法执行完后并将x,v写回主内存
         * 后，再执行reader方法，继续访问v，此时的结 果就是x=0,v=true。 另外，
         * result="x="+x+",v="+v;也会严格安照程序的顺序来执行访问操作（即volatile不
         * 只是在写回内存时是按程序语义的执行顺序来执行，在读的时候也是这样 要按照程序
         * 的访问顺序来，但如果不是volatile变量时，则read动作就可能不会按照程序顺序来
         * 执行，但这好像对纯粹的访问操作没有什么影响，这好只有访问操作的不变对象一样
         * ，不会出现线程不安全的问题），即先访问x后再能访问v，但这绝不是原子性的，很
         * 有可能从他们中 间 切换到其他线程。

         * 另外，在测试的过程中发现writer方法的原子性要比reader的原子性要强，即多个访
         * 问操作在 一起不如多个赋值语句原子性强
         */
        result = "x=" + x + ",v=" + v;
        //result = "v=" + v + ",x=" + x;
    }

}