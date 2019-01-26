package com.william.algorithm.middle;

/**
 * Created by william.zhang on 2015/9/7.
 */
public class BinaryNode {
    protected Object element;
    protected BinaryNode leftNode;
    protected BinaryNode rightNode;
    protected BinaryNode parent;

    public BinaryNode() {
	}
    
    public BinaryNode(Object element) {
		super();
		this.element = element;
	}

	public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public BinaryNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BinaryNode leftNode) {
        this.leftNode = leftNode;
    }

    public BinaryNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(BinaryNode rightNode) {
        this.rightNode = rightNode;
    }

    public BinaryNode getParent() {
        return parent;
    }

    public void setParent(BinaryNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "BinaryNode{" +
                "element=" + element +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                ", parent=" + parent +
                '}';
    }
}
