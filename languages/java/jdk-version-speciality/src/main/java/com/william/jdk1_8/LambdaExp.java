package com.william.jdk1_8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by william on 17-7-13.
 *
 * http://www.cnblogs.com/zkycode/p/6251488.html
 */
public class LambdaExp {

    public static void main(String[] args) {
        List<A> aList = new ArrayList<>();
        List<Long> list = aList.stream().map(A::getId).collect(Collectors.toList());
        //需要事先判断一下，stream一定会开启一个数据流的过程么.
        System.out.println(list == Collections.EMPTY_LIST);
    }

    static class A {
        private long id;
        private long value;

        public A() {
        }

        public A(long id, long value) {
            this.id = id;
            this.value = value;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "A{" +
                    "id=" + id +
                    ", value=" + value +
                    '}';
        }
    }
}
