package com.william.algorithm.middle;

import java.util.Stack;

/**
 * @Package com.william.algorithm.middle
 * @Description:
 * @Author deepen.zhang
 * @Date 2021/8/11 11:48
 * @Version V1.0
 */
public class BinaryNodeSortTest {

    public static class Node {
        private Node left;
        private Node right;
        private Object value;

        public Node() {
            super();
        }

        public Node(Object value) {
            this.value = value;
        }

        public Node(Node left, Node right, Object value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "left=" + left +
                    ", right=" + right +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node n1 = new Node(10);
        Node n2 = new Node(20);
        Node n3 = new Node(30);
        Node n4 = new Node(40);
        Node n5 = new Node(50);
        Node n6 = new Node(60);
        n1.setLeft(n2);
        n1.setRight(n3);
        n2.setLeft(n4);
        n2.setRight(n5);
        n3.setLeft(n6);

//        preVisitNode(n1);
        preVisitWithCycle(n1);
    }

    private static void preVisitNode(Node root) {
        if(root == null) {
            return;
        }
        preVisitNode(root.left);
        System.out.println(String.format("node: %s ", root.getValue()));
        preVisitNode(root.right);
    }

    private static void preVisitWithCycle(Node root) {
        if(root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node curNode = root;
        while (curNode != null) {
            while (curNode != null) {
                System.out.println(String.format("node: %s ", curNode.getValue()));
                if(curNode.right != null) {
                    stack.push(curNode.right);
                }
                curNode = curNode.left;
            }
            if(!stack.isEmpty()) {
                curNode = stack.pop();
            }
        }
    }

}
