package com.william.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * @Package com.william.algorithm
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/17 02:47
 * @Version V1.0
 */
public class Test {

    private static int x = 100;

    public static void main(String[] args) {
        Test t1 = new Test();
        t1.x ++;
        Test t2 = new Test();
        t2.x ++;
        t1 = new Test();
        t1.x ++;
        Test.x ++;
        System.out.println(x);
        int  i = 12;
        System.out.println(i*=++i);
        float f = 0.3f;
//      System.out.println(f >> 1); 编译失败
        System.out.println(2>>1);
        System.out.println('A' + i + f);
        List<String> a = Arrays.asList("s");

        System.out.println(a.getClass());
        int n = 0;
        for (int j = 0; j <= n; j++) {
            for (int k = n; j > i; k--) {
                System.out.println(1);
            }
        }
    }
}
