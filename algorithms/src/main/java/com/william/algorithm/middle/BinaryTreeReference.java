package com.william.algorithm.middle;

import java.util.List;

/**
 * 
 * @author zdpwilliam
 * 1、recursive and divide-and-conquer thinking
 * 2、the smallest cell is a node with left or right
 *
 */
public class BinaryTreeReference {
	
	/**
	 * calculate BinaryTree depth : 
	 * @param tnode
	 * @return
	 */
	public static int depth(BinaryNode tnode) {
		if(tnode == null)
			return 0;
		int leftDepth = depth(tnode.getLeftNode());
		int rigthDepth = depth(tnode.getRightNode());
		return ( leftDepth > rigthDepth ? leftDepth : rigthDepth) + 1;
	}
	
	/**
	 * calculate BinaryTree number of node : 
	 * @param tnode
	 * @return
	 */
	public static int numOfNodes(BinaryNode tnode) {
		if(tnode == null)
			return 0;
		return 1 + numOfNodes(tnode.getLeftNode()) + numOfNodes(tnode.getRightNode());
	}
	
	/**
	 * calculate BinaryTree number of leaf : 
	 * @param tnode
	 * @return
	 */
	public static int numOfleaf(BinaryNode tnode) {
		if(tnode == null)
			return 0;

		if(tnode.getLeftNode() == null && tnode.getRightNode() == null)
			return 1;

		return numOfleaf(tnode.getLeftNode()) + numOfleaf(tnode.getRightNode());
	}
	
	public static void main(String[] args) {
		//create a binary tree
		BinaryNode n1 = new BinaryNode("1");
		BinaryNode n2 = new BinaryNode("2");
		BinaryNode n3 = new BinaryNode("3");
		BinaryNode n4 = new BinaryNode("4");
		BinaryNode n5 = new BinaryNode("5");
		
		n1.setLeftNode(n2);
		n1.setRightNode(n3);
		n3.setLeftNode(n4);
		n3.setRightNode(n5);
		
		System.out.println("binary tree depth: " + depth(n1));
		System.out.println("binary tree node count: " + numOfNodes(n1));
		System.out.println("binary tree leaf count: " + numOfleaf(n1));
	}

	/**
	 二叉树(binary tree)
	 	每个结点的度均不超过 2 的有序树,称为二叉树(binary tree)。
	 	递归定义 二叉树或者是一棵空树,或者是一棵由一个根结点和两棵互不相交
	 的分别称为根的左子树和右子树的子树所组成的非空树。
        二叉树中每个结点的孩子数只能是 0、1 或 2 个
	 */
    abstract class BinaryTreeADT {
        //(1) getSzie () 输入参数:无 返回参数:非负整数 功能:返回二叉树的结点数
        public abstract int getSzie ();

        //(2) isEmpty () 输入参数:无 返回参数:boolean 功能:判断二叉树是否为空
        public abstract boolean isEmpty ();

        //(3) getRoot() 输入参数:无 返回参数:结点 功能:返回二叉树的树根结点
        public abstract BinaryNode getRoot ();

        //(4) getHeight() 输入参数:无 返回参数:整数 功能:返回二叉树的高度
        public abstract int getHeight ();

        //(5) find(e) 输入参数:元素 e 返回参数:结点 功能:找到数据元素 e 所在结点。若 e 不存在,则返回空
        public abstract boolean find(BinaryNode e);

        //(6) 输入参数:无 返回参数:迭代器对象 功能:先序、中序、后序、按层遍历 x 为根的二叉树。结果由 迭代器对象返回
        public abstract List<BinaryNode> preOrder(BinaryNode x);
        public abstract List<BinaryNode> inOrder(BinaryNode x);
        public abstract List<BinaryNode> postOrder(BinaryNode x);
        public abstract List<BinaryNode> levelOrder(BinaryNode x);
	}

    /**
     二叉树的性质
     性质1) 在二叉树的第i层上最多有 2i 个结点
     性质2) 高度为h的二叉树至多有 2的h+1次幂 -1 个结点
     性质3) 对任何一棵二叉树T,如果其终端结点数为 n0 ,度为 2 的结点数为 n2 ,则n0 = n2 + 1

     满二叉树 完全二叉树性质
     性质1) 有 n 个结点的完全二叉树的高度为 ?log n?

        从完全二叉树的定义不难得到以下观察结论:在固定结点数目的二叉树中,完全二叉树的高度是最小的。
     性质2) 含有 n≥1 个结点的完全二叉树的高度至多为 n-1;高度至少为 ?log n?

     性质3) 如果对一棵有 n 个结点的完全二叉树的结点进行编号,则对任一结点 i(1≤ i ≤n),有:
         (A) 如果 i=1,则结点 i 是二叉树的根,无双亲; 如果 i>1,则其双亲结点 PARENT(i)是结点 ?i / 2?
         (B) 如果 2i>n,则结点 i 无左孩子;否则其左孩子是结点 2i
         (C) 如果 2i+1>n,则结点 i 无右孩子;否则其右孩子是结点 2i+1
     */

}
