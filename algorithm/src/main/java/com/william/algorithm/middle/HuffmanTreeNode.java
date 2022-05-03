package com.william.algorithm.middle;

/**
 * Created by william on 17-6-29.
 */
public class HuffmanTreeNode extends BinaryNode {

    private Integer weight;     //权值
    private String coding = ""; //编码

    //构造方法
    public HuffmanTreeNode(int weight) {
        this(weight, null);
    }

    public HuffmanTreeNode(int weight, Object e) {
        super(e);
        this.weight = weight;
    }

    //改写父类方法
    /*public HuffmanTreeNode getParent() {
        return (HuffmanTreeNode) super.getParent();
    }*/

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    @Override
    public String toString() {
        return "HuffmanTreeNode{" +
                "weight=" + weight +
                ", coding='" + coding + '\'' +
                "} " + super.toString();
    }
}
