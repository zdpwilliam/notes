package com.william.algorithm.middle;

import java.util.Iterator;

/**
 * Created by william on 17-6-29.
 */
public class GraphReference {
    /**
     *  图(graph)是一种网状数据结构,图是由非空的顶点集合和一个描述顶点之间关系的集合组成。
     *      Graph = ( V , E )
     *      V = {x| x∈某个数据对象}
     *      E = {<u , v>| P(u , v)∧(u,v∈V)}
     *      V 是具有相同特性的数据元素的集合,V 中的数据元素通常称为顶点(Vertex),R 是
     *  两个顶点之间关系的集合。P(u , v)表示 u 和 v 之间有特定的关联属性。
     *
     *  有向图(directed graph)     无向图(undirected graph)
     *
     *  简单图中所有的边自然构成一个集合,并且每条边的两个顶点均不相。
     *  某条边的两个顶点是同一个顶点，则不是 简单图。
     *
     *  邻接点     (u, v)与顶点 u 和 v 相关联； u 和 v 互称 邻接点
     *
     *  顶点的度、入度、出度
     *      顶点的度(degree)是指依附于某顶点 v 的边数,通常记为 TD (v)
     *      顶点 v 的入度(in degree)是指以顶点为终点的边的数目,记为 ID (v)
     *      顶点 v 出度(out degree)是指以顶点 v 为起始点的边的数目,记为 OD (v)
     *  观察结论 7.1 在任何图G = (V, E)中,|E| = (∑TD(vi))/2。
     *
     *  完全图 、稠密图、稀疏图
     *  观察结论 7.2 假设在图 G = (V, E)中有 n 个顶点和 m 条边
     *      1) 若 G 是无向图,则有 0 ≤ m ≤ n(n-1)/2
     *      2) 若 G 是有向图,则有 0 ≤ m ≤ n(n-1)
     *     由此,在具有n个顶点的图中,边的数目为Ο(n 2 )。由于图中边数与顶点数并非线性关系,
     *     因此在对有关图的算法时间复杂度、空间复杂度进行分析时,我们往往以图中的顶点数和边
     *  数作为问题的规模。
     *     有 n(n-1)/2 条边的无向图称为无向完全图;有 n(n-1)条边的有向图称为有向完全图。有
     *  很少边(如 m < n log n)的图称为稀疏图,反之边较多的图称为稠密图。
     *
     *  子图(subgraph) 生成子图(spanning subgraph)
     *  路径、环路及可达分量
     *  连通性与连通分量
     *  无向图的生成树  对于无向图 G = ( V , E )。如果 G 是连通图,则 G 的生成树(spanning tree),
     *  是 G 的一个极小连通生成子图。
     */
    abstract class GraphADT {
        // 数据对象 D:D 是具有相同性质的数据元素的集合。
        // 数据关系 R:R = {<u , v>| P(u , v)∧(u,v∈D)}
        // 基本操作:
        public abstract int getType();
        //返回图的顶点数
        public abstract int getVexNum();
        //返回图的边数
        public abstract int getEdgeNum();
        //返回图的所有顶点
        public abstract Iterator getVertex();
        //返回图的所有边
        public abstract Iterator getEdge();
        //删除一个顶点 v
        public abstract void remove(Vertex v);
        //删除一条边 e
        public abstract void remove(Edge e);
        //添加一个顶点 v
        public abstract Node insert(Vertex v);
        //添加一条边 e
        public abstract Node insert(Edge e);
        //判断顶点 u、v 是否邻接,即是否有边从 u 到 v
        public abstract boolean areAdjacent(Vertex u, Vertex v);
        //返回从 u 指向 v 的边,不存在则返回 null
        public abstract Edge edgeFromTo(Vertex u, Vertex v);
        //返回从 u 出发可以直接到达的邻接顶点
        public abstract Iterator adjVertexs(Vertex u);
        //对图进行深度优先遍历
        public abstract Iterator DFSTraverse(Vertex v);
        //对图进行广度优先遍历
        public abstract Iterator BFSTraverse(Vertex v);
        //求顶点 v 到其他顶点的最短路径
        public abstract Iterator shortestPath(Vertex v);
        //求无向图的最小生成树,如果是有向图不支持此操作
        public abstract void generateMST() throws UnsupportedOperation;
        //求有向图的拓扑序列,无向图不支持此操作
        public abstract Iterator toplogicalSort() throws UnsupportedOperation;
        //求有向无环图的关键路径,无向图不支持此操作
        public abstract void criticalPath() throws UnsupportedOperation;
    }

    public interface Graph {
        public static final int UndirectedGraph = 0;//无向图
        public static final int DirectedGraph = 1;  //有向图
        //返回图的类型
        public int getType();
        //返回图的顶点数
        public int getVexNum();
        //返回图的边数
        public int getEdgeNum();
        //返回图的所有顶点
        public Iterator getVertex();
        //返回图的所有边
        public Iterator getEdge();
        //删除一个顶点 v
        public void remove(Vertex v);
        //删除一条边 e
        public void remove(Edge e);
        //添加一个顶点 v
        public Node insert(Vertex v);
        //添加一条边 e
        public Node insert(Edge e);
        //判断顶点 u、v 是否邻接,即是否有边从 u 到 v
        public boolean areAdjacent(Vertex u, Vertex v);
        //返回从 u 指向 v 的边,不存在则返回 null
        public Edge edgeFromTo(Vertex u, Vertex v);
        //返回从 u 出发可以直接到达的邻接顶点
        public Iterator adjVertexs(Vertex u);
        //对图进行深度优先遍历
        public Iterator DFSTraverse(Vertex v);
        //对图进行广度优先遍历
        public Iterator BFSTraverse(Vertex v);
        //求顶点 v 到其他顶点的最短路径
        public Iterator shortestPath(Vertex v);
        //求无向图的最小生成树,如果是有向图不支持此操作
        public void generateMST() throws UnsupportedOperation;
        //求有向图的拓扑序列,无向图不支持此操作
        public Iterator toplogicalSort() throws UnsupportedOperation;
        //求有向无环图的关键路径,无向图不支持此操作
        public void criticalPath() throws UnsupportedOperation;
    }

    class Node {}       //节点
    class Vertex {}     //顶点
    class Edge {}       //边
    class UnsupportedOperation extends Exception {
        public UnsupportedOperation(String err) {
            super(err);
        }
    }
}
