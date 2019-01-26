package com.william.algorithm.middle;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by william on 17-6-28.
 */
public class BinaryTreeTraverse {

    /**
     * (1) 先序遍历(DLR)二叉树的操作定义为:
     * 若二叉树为空,则空操作;否则
     * 1 访问根结点;
     * 2 先序遍历左子树;
     * 3 先序遍历右子树。
     * (2) 中序遍历(LDR)二叉树的操作定义为:
     * 若二叉树为空,则空操作;否则
     * 1 中序遍历左子树;
     * 2 访问根结点;
     * 3 中序遍历右子树。
     * (3) 后序遍历(LRD)二叉树的操作定义为:
     * 若二叉树为空,则空操作;否则
     * 1 后序遍历左子树;
     * 2 后序遍历右子树;
     * 3 访问根结点。
     * <p>
     * 二叉树表达式树
     * 递归定义如下:若表达式为数或简单变量,则相应二叉树中仅有一个根结点;若表达式=(第
     * 一操作数)(运算符)(第二操作数),则相应二叉树用左子树表示第一操作数,用右子树表
     * 示第二操作数,根结点存放运算符。
     */

    public static void main(String[] args) {
        //create a binary express tree demo: a+(b-c)×d-e/f
        BinaryNode na = new BinaryNode("a");
        BinaryNode nb = new BinaryNode("b");
        BinaryNode nc = new BinaryNode("c");
        BinaryNode nd = new BinaryNode("d");
        BinaryNode ne = new BinaryNode("e");
        BinaryNode nf = new BinaryNode("f");

        BinaryNode nsp = new BinaryNode("+");
        BinaryNode root = new BinaryNode("-");
        BinaryNode nsm = new BinaryNode("*");
        BinaryNode nsdv = new BinaryNode("/");
        BinaryNode nsd = new BinaryNode("-");

        root.setLeftNode(nsp);
        root.setRightNode(nsdv);

        nsp.setLeftNode(na);
        nsp.setRightNode(nsm);

        nsm.setLeftNode(nsd);
        nsm.setRightNode(nd);

        nsd.setLeftNode(nb);
        nsd.setRightNode(nc);

        nsdv.setLeftNode(ne);
        nsdv.setRightNode(nf);

        LinkedList preRecList = new LinkedList();
        preOrderRecursion(root, preRecList);
        System.out.println("pre order recursion traverse : " + printSentence(preRecList));

        LinkedList preList = new LinkedList();
        preOrderTraverse(root, preList);
        System.out.println("pre order traverse : " + printSentence(preList));


        LinkedList inRecList = new LinkedList();
        inOrderTraverse(root, inRecList);
        System.out.println("in order recursion traverse : " + printSentence(inRecList));

        LinkedList inList = new LinkedList();
        inOrderRecursion(root, inList);
        System.out.println("in order traverse : " + printSentence(inList));


        LinkedList postRecList = new LinkedList();
        postOrderRecursion(root, postRecList);
        System.out.println("post order recursion traverse : " + printSentence(postRecList));

        LinkedList postList = new LinkedList();
        postOrderTraverse(root, postList);
        System.out.println("post order traverse : " + printSentence(postList));


        LinkedList levelList = new LinkedList();
        levelOrderTraverse(root, levelList);
        System.out.println("level order traverse : " + printSentence(levelList));

        System.out.println("find out e : " + searchE(root, "*"));
        System.out.println("find out e : " + searchE(root, "d"));


        BinaryNode nA = new BinaryNode("A");
        BinaryNode nB = new BinaryNode("B");
        BinaryNode nC = new BinaryNode("C");
        BinaryNode nD = new BinaryNode("D");
        BinaryNode nE = new BinaryNode("E");
        BinaryNode nF = new BinaryNode("F");
        BinaryNode nG = new BinaryNode("G");
        BinaryNode nH = new BinaryNode("H");

        nA.setLeftNode(nB);
        nA.setRightNode(nC);

        nB.setLeftNode(nD);
        nB.setRightNode(nE);

        nC.setLeftNode(nG);
        nC.setRightNode(nH);

        LinkedList<BinaryNode> linkedList = new LinkedList<BinaryNode>();
        preOrderRecursion(nA, linkedList);
        System.out.println("preOrderRecursion " + printSentence(linkedList));
    }

    /**
     * 拼接字符串
     *
     * @param sourceList
     * @return
     */
    private static String printSentence(LinkedList<BinaryNode> sourceList) {
        StringBuilder sb = new StringBuilder();
        if (sourceList != null) {
            Iterator<BinaryNode> binaryNodeIterator = sourceList.listIterator();
            while (binaryNodeIterator.hasNext()) {
                BinaryNode node = binaryNodeIterator.next();
                sb.append(node.getElement()).append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 先序遍历的递归算法
     *
     * @param root
     * @param containList
     */
    private static void preOrderRecursion(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) {
            return;
        }
        containList.add(root);
        preOrderRecursion(root.getLeftNode(), containList);
        preOrderRecursion(root.getRightNode(), containList);
    }

    /**
     * 先序遍历的非递归算法
     * 思路: 内循环向左一直遍历到尽头，遇到右子树就入栈，待外层循环逐个出栈遍历。
     *      内循环非空就放入结果集
     */
    private static void preOrderTraverse(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) return;
        BinaryNode p = root;
        Stack s = new Stack();
        while (p != null) {
            while (p != null) {
                //向左走到尽头
                containList.add(p);
                //访问根
                if (p.getRightNode() != null) s.push(p.getRightNode());
                //右子树根结点入栈
                p = p.getLeftNode();
            }
            if (!s.isEmpty()) p = (BinaryNode) s.pop();//右子树根退栈遍历右子树
        }
    }

    /**
     * 中序遍历的递归算法
     *
     * @param root
     * @param containList
     */
    private static void inOrderRecursion(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) {
            return;
        }
        inOrderRecursion(root.getLeftNode(), containList);
        containList.add(root);
        inOrderRecursion(root.getRightNode(), containList);
    }

    /**
     * 中序遍历的非递归算法
     * 思路：    内循环向左一直遍历到尽头，遇到左节点就入栈，直到叶节点(包括叶节点);
     *      之后逐个出栈放入结果集，进入外循环逐个遍历栈中右子树。
     *          内循环遍历左子树并入栈，外循环出栈便利右子树
     *
     */
    private static void inOrderTraverse(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) return;
        BinaryNode p = root;
        Stack s = new Stack();
        while (p != null || !s.isEmpty()) {
            while (p != null) {
                //一直向左走
                s.push(p);
                //将根结点入栈
                p = p.getLeftNode();
            }
            if (!s.isEmpty()) {
                p = (BinaryNode) s.pop(); //取出栈顶根结点访问之
                containList.add(p);
                p = p.getRightNode();
                //转向根的右子树进行遍历
            }//if
        }//out while
    }

    /**
     * 后续序遍历的递归算法
     *
     * @param root
     * @param containList
     */
    private static void postOrderRecursion(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) {
            return;
        }
        postOrderRecursion(root.getLeftNode(), containList);
        postOrderRecursion(root.getRightNode(), containList);
        containList.add(root);
    }

    /**
     * 后序遍历的非递归算法
     * 思路：    内循环向左一直遍历到尽头，遇到节点就入栈，直到叶节点(包括叶节点);
     *      之后取栈顶放入结果集，之后转向栈顶根结点的右子树继续后序遍历，出栈顺序遍历右子树。
     *          内循环遍历左子树并入栈
     *          外循环按出栈顺序遍历右子树
     */
    private static void postOrderTraverse(BinaryNode root, LinkedList<BinaryNode> containList) {
        if (root == null) return;
        BinaryNode p = root;
        Stack s = new Stack();
        while (p != null || !s.isEmpty()) {
            while (p != null) {
                //先左后右不断深入
                s.push(p);
                //将根节点入栈
                if (p.getLeftNode() != null) p = p.getLeftNode();
                else p = p.getRightNode();
            }
            if (!s.isEmpty()) {
                p = (BinaryNode) s.pop();
                //取出栈顶根结点访问之
                containList.add(p);
            }
            //满足条件时,说明栈顶根节点右子树已访问,应出栈访问之
            while (!s.isEmpty() && ((BinaryNode) s.peek()).getRightNode() == p) {
                p = (BinaryNode) s.pop();
                containList.add(p);
            }
            //转向栈顶根结点的右子树继续后序遍历
            if (!s.isEmpty()) p = ((BinaryNode) s.peek()).getRightNode();
            else p = null;
        }
    }

    /**
     * 使用队列完成二叉树的按层遍历
     * 思路： 使用队列保证左右子树的遍历顺序
     *
     * @param root
     * @param containList
     */
    private static void levelOrderTraverse(BinaryNode root, LinkedList containList) {
        if (root == null) return;
        Queue q = new LinkedBlockingQueue();
        q.offer(root);
        //根结点入队
        while (!q.isEmpty()) {
            BinaryNode p = (BinaryNode) q.poll();
            //取出队首结点 p 并访问
            containList.add(p);
            if (p.getLeftNode() != null) q.offer(p.getLeftNode());  //将p的非空左右孩子依次入队
            if (p.getRightNode() != null) q.offer(p.getRightNode());
        }
    }

    /**
     * 递归查找元素 e
     *
     * @param root
     * @param e
     * @return
     */
    private static BinaryNode searchE(BinaryNode root, Object e) {
        if (root == null) return null;
        if (root.getElement() == e) return root; //如果是根结点,返回根
        BinaryNode v = searchE(root.getLeftNode(), e);        //否则在左子树中找
        if (v == null) v = searchE(root.getRightNode(), e);    //没找到,在右子树中找
        return v;
    }
}
