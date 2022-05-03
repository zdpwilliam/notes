package com.william.algorithm.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.william.algorithm.middle
 * @Description: TODO
 * @Author deepen.zhang
 * @Date 2021/9/15 06:54
 * @Version V1.0
 */
public abstract class Test {

    static {
        System.out.println(1);
    }

    public Test() {
        System.out.println(2);
    }

    public static class Sub extends Test {

        static {
            System.out.println("a");
        }



        public Sub() {
            System.out.println("b");
        }

        public static void main(String[] args) {
            Test test = new Sub();
            test = new Sub();

            List<Integer> a = new ArrayList<>();
            a.add(1);
            a.add(2);
            a.add(new Integer(1));
            System.out.println(a);

        }
    }
}
