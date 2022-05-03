package com.william.example.volatilemistakes;

public class VolatileSample1 {
    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        /* 由于volatile的特点，这里要想v为true，则x的肯定已经执行赋值（assign）动作
         * 且已写回（writer）主内了，所以不会出现 v=true，x=0的情形。但时，如果这里先
         * 访问的是x变量，则由于volatile不具有原子性，则还是会出现v=true，x=0的情形，
         * 具体请看后面测试
         */
        if (v == true) {
            //uses x - 确保能看见42.
        }
    }
}