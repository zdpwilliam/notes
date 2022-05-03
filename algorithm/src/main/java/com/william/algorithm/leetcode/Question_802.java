package com.william.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.william.algorithm.leetcode
 * @Description: https://leetcode-cn.com/problems/find-eventual-safe-states/
 * @Author deepen.zhang
 * @Date 2021/8/7 14:14
 * @Version V1.0
 */
public class Question_802 {

    public static void main(String[] args) {
        int[][] graph = new int[][] {
            new int[]{1,2},
            new int[]{2,3},
            new int[]{5},
            new int[]{0},
            new int[]{5},
            new int[]{},
            new int[]{},
        };

        /**
         *  1. 图的邻接矩阵 DFS + 三色标记
         */
        System.out.println(new Question_802().eventualSafeNodes1(graph));

        /**
         *  2. 图的拓扑排序
         */
        System.out.println(new Question_802().eventualSafeNodes2(graph));
    }

    public List<Integer> eventualSafeNodes2(int[][] graph) {
        List<Integer> targetList = new ArrayList<>();
        //n: graph 是图的节点数，n=graph.length
        //i: 表示 graph（图）的节点
        //j: 表示 graph（图）的 节点到节点 的有向边(i, j)的列表
        
        return targetList;
    }


    public List<Integer> eventualSafeNodes1(int[][] graph) {
        List<Integer> targetList = new ArrayList<>();
        //n: graph 是图的节点数，n=graph.length
        //i: 表示 graph（图）的节点
        //j: 表示 graph（图）的 节点到节点 的有向边(i, j)的列表
        int n = graph.length;
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            //(i, j)
            if(isSafeNode(graph, color, i)) {
                targetList.add(i);
            }
        }
        return targetList;
    }

    /**
     * 白色（用 0 表示）：该节点尚未被访问；
     * 灰色（用 1 表示）：该节点位于递归栈中，或者在某个环上；
     * 黑色（用 2 表示）：该节点搜索完毕，是一个安全节点。
     * @return
     */
    private boolean isSafeNode(int[][] graph, int[] color, int visit) {
        if(color[visit] > 0) {
            return color[visit] == 2;
        }
        color[visit] = 1;
        for (int v : graph[visit]) {
            if(!isSafeNode(graph, color, v)) {
                return false;
            }
        }
        color[visit] = 2;
        return true;
    }

}
