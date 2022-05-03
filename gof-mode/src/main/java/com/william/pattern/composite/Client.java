package com.william.pattern.composite;

/**
 * Created by william.zhang on 2016/2/19.
 */
public class Client {

    public static void main(String[] args) {
        withCompositePattern();
//        withOutCompositePattern();
    }

    public static void withCompositePattern() {
        Component root = new Composite("服装");

        Component c1 = new Composite("男装");
        Component c2 = new Composite("女装");

        Component l1 = new Leaf("衬衣");
        Component l2 = new Leaf("夹克");
        Component l3 = new Leaf("裙子");
        Component l4 = new Leaf("套装");

        root.addChild(c1);
        root.addChild(c2);
        c1.addChild(l1);
        c1.addChild(l2);
        c2.addChild(l3);
        c2.addChild(l4);

        root.printStruct("");
    }

    public static void withOutCompositePattern() {
        CompositeOld root = new CompositeOld("服装");

        CompositeOld c1 = new CompositeOld("男装");
        CompositeOld c2 = new CompositeOld("女装");

        LeafOld l1 = new LeafOld("衬衣");
        LeafOld l2 = new LeafOld("夹克");
        LeafOld l3 = new LeafOld("裙子");
        LeafOld l4 = new LeafOld("套装");

        root.addComposite(c1);
        root.addComposite(c2);
        c1.addLeaf(l1);
        c1.addLeaf(l2);
        c2.addLeaf(l3);
        c2.addLeaf(l4);

        root.printStruct("");
    }
}
