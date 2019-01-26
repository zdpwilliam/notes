package com.william.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.zhang on 2016/2/19.
 */
public class Composite extends Component {
    private List<Component> childComponents = null;

    private String name;

    public Composite(String name) {
        this.name = name;
    }

    @Override
    public void printStruct(String preStr) {
        System.out.println(preStr + "+" + name);
        preStr += " ";
        if (childComponents != null) {
            for (Component component : childComponents) {
                component.printStruct(preStr);
            }
        }
    }

    @Override
    public void addChild(Component child) {
        if(childComponents == null) {
            childComponents = new ArrayList<Component>();
        }
        childComponents.add(child);
    }

    @Override
    public void removeChild(Component child) {
        if(childComponents != null) {
            childComponents.remove(child);
        }
    }

    @Override
    public Component getChild(int index) {
        if(childComponents != null) {
            if(index >= 0 && index < childComponents.size()) {
                return childComponents.get(index);
            }
        }
        return null;
    }
}
