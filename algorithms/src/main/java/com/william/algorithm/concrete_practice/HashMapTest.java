package com.william.algorithm.concrete_practice;

import java.util.HashMap;

/**
 * @Package com.william.algorithm.concrete_practice
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/9/18 09:02
 * @Version V1.0
 */
public class HashMapTest {

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> map = new HashMap<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    map.put("a", "a");
                    System.out.println("put ok !");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    map.remove("a");
                    System.out.println("remove ok !" + map.remove("a"));
                }
            }
        }).start();
        Thread.sleep(Long.MAX_VALUE);
    }

}
