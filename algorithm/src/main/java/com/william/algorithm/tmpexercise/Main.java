package com.william.algorithm.tmpexercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Package com.william.algorithm.tmpexercise
 * @Description
 * @Author deepen.zhang
 * @Date 2021/8/13 16:28
 * @Version V1.0
 */
public class Main {

    public static void main(String[] args) {
        Node root = new Node(10);
        Node n1 = new Node(23);
        Node n2 = new Node(19);
        Node n3 = new Node(8);
        Node n4 = new Node(43);
        Node n5 = new Node(52);
        Node n6 = new Node(77);
        Node n7 = new Node(93);
        root.left = n1;
        root.right = n2;
        n2.left = n3;
        n3.left = n4;
        n3.right = n5;
        n4.right = n6;
        n2.right = n7;
        System.out.println(findMaxPath(root));
    }

    public static int findMaxPath(Node root) {
        List<Integer> pathList = new ArrayList<>();
        calculateBinaryTree(root, 0, pathList);
        Collections.sort(pathList);
        return pathList.get(pathList.size() - 1);
    }

    private static void calculateBinaryTree(Node node, int sum, List<Integer> pathList) {
        if(node == null) {
            pathList.add(sum);
            return;
        }
        sum += node.weight;
        calculateBinaryTree(node.left, sum, pathList);
        calculateBinaryTree(node.right, sum, pathList);
    }

    public static class Node {
        int weight;
        Node left;
        Node right;

        public Node() {
        }

        public Node(int weight) {
            this.weight = weight;
        }
    }
}
