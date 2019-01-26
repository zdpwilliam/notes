package com.william.pattern.composite.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.zhang on 2016/2/23.
 */
public class Client {

    public static void main(String[] args) {
        List<Privilege> sourcePrivileges = new ArrayList<Privilege>();
        for (int i = 0; i < 20; i++) {
            sourcePrivileges.add(new Privilege());
        }
        System.out.print("---------------------");
    }

}
