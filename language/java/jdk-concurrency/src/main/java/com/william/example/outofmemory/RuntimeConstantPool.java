package com.william.example.outofmemory;

import java.util.ArrayList;
import java.util.List;

/**
 *  VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPool {

    public static void main(String[] args) {
        // 1.6 false false  intern()实现会复制前边的字符串到 永久代
        // 1.7 1.8 ture false 中常量池intern()实现则不会复制，只是在常量池中首次出现实例引用
        String str1 = new StringBuilder("计算器").append("软件").toString();
        System.out.println(str1.intern() == str1);//true

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);//false是因为之前常量池首次出现过"java"，而其引用和当前str2的引用不同。
    }

    // 1.8 虚拟机永久代发生变化，此溢出demo可能存在问题
    public void outOfMemory() {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
