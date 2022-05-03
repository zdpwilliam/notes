package com.william.pattern.composite;

/**
 * Created by william.zhang on 2016/2/19.
 */
public abstract class Component {

    public abstract void printStruct(String preStr);

    public void addChild(Component child) {
        throw new UnsupportedOperationException();
    }

    public void removeChild(Component child) {
        throw new UnsupportedOperationException();
    }

    public Component getChild(int index) {
        throw new UnsupportedOperationException();
    }
}
