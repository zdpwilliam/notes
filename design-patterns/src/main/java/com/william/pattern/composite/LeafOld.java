package com.william.pattern.composite;

/**
 * Created by william.zhang on 2016/2/19.
 */
public class LeafOld {
    private String name;

    public LeafOld(String name) {
        this.name = name;
    }

    public void printStruct(String preStr) {
        System.out.println(preStr + "-" + name);
    }
}
