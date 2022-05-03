package com.william.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.zhang on 2016/2/19.
 */
public class CompositeOld {

    private List<CompositeOld> childComposite = new ArrayList<CompositeOld>();

    private List<LeafOld> childLeaf = new ArrayList<LeafOld>();

    private String name;

    public CompositeOld(String name) {
        this.name = name;
    }

    public void addComposite(CompositeOld composite) {
        childComposite.add(composite);
    }

    public void addLeaf(LeafOld leaf) {
        childLeaf.add(leaf);
    }

    public void printStruct(String preStr) {
        System.out.println(preStr + "+" + name);

        preStr += " ";

        for (LeafOld leaf : childLeaf) {
            leaf.printStruct(preStr);
        }

        for (CompositeOld composite : childComposite) {
            composite.printStruct(preStr);
        }
    }
}
