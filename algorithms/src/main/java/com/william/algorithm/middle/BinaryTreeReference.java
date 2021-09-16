package com.william.algorithm.middle;

import java.util.List;

/**
 * 
 * @author zdpwilliam
 * 1��recursive and divide-and-conquer thinking
 * 2��the smallest cell is a node with left or right
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
	 ������(binary tree)
	 	ÿ�����ĶȾ������� 2 ��������,��Ϊ������(binary tree)��
	 	�ݹ鶨�� ������������һ�ÿ���,������һ����һ�����������û����ཻ
	 �ķֱ��Ϊ����������������������������ɵķǿ�����
        ��������ÿ�����ĺ�����ֻ���� 0��1 �� 2 ��
	 */
    abstract class BinaryTreeADT {
        //(1) getSzie () �������:�� ���ز���:�Ǹ����� ����:���ض������Ľ����
        public abstract int getSzie ();

        //(2) isEmpty () �������:�� ���ز���:boolean ����:�ж϶������Ƿ�Ϊ��
        public abstract boolean isEmpty ();

        //(3) getRoot() �������:�� ���ز���:��� ����:���ض��������������
        public abstract BinaryNode getRoot ();

        //(4) getHeight() �������:�� ���ز���:���� ����:���ض������ĸ߶�
        public abstract int getHeight ();

        //(5) find(e) �������:Ԫ�� e ���ز���:��� ����:�ҵ�����Ԫ�� e ���ڽ�㡣�� e ������,�򷵻ؿ�
        public abstract boolean find(BinaryNode e);

        //(6) �������:�� ���ز���:���������� ����:�������򡢺��򡢰������ x Ϊ���Ķ������������ ���������󷵻�
        public abstract List<BinaryNode> preOrder(BinaryNode x);
        public abstract List<BinaryNode> inOrder(BinaryNode x);
        public abstract List<BinaryNode> postOrder(BinaryNode x);
        public abstract List<BinaryNode> levelOrder(BinaryNode x);
	}

    /**
     ������������
     ����1) �ڶ������ĵ�i��������� 2i �����
     ����2) �߶�Ϊh�Ķ����������� 2��h+1���� -1 �����
     ����3) ���κ�һ�ö�����T,������ն˽����Ϊ n0 ,��Ϊ 2 �Ľ����Ϊ n2 ,��n0 = n2 + 1

     �������� ��ȫ����������
     ����1) �� n ��������ȫ�������ĸ߶�Ϊ ?log n?

        ����ȫ�������Ķ��岻�ѵõ����¹۲����:�ڹ̶������Ŀ�Ķ�������,��ȫ�������ĸ߶�����С�ġ�
     ����2) ���� n��1 ��������ȫ�������ĸ߶�����Ϊ n-1;�߶�����Ϊ ?log n?

     ����3) �����һ���� n ��������ȫ�������Ľ����б��,�����һ��� i(1�� i ��n),��:
         (A) ��� i=1,���� i �Ƕ������ĸ�,��˫��; ��� i>1,����˫�׽�� PARENT(i)�ǽ�� ?i / 2?
         (B) ��� 2i>n,���� i ������;�����������ǽ�� 2i
         (C) ��� 2i+1>n,���� i ���Һ���;�������Һ����ǽ�� 2i+1
     */

}
