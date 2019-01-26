package com.william.cluster.consistent.hash;

/**
 * Created by william on 17-7-6.
 */
public class NormalHashCluster extends Cluster {

    public NormalHashCluster() {
        super();
    }

    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    @Override
    public void removeNode(Node delNode) {
        this.nodes.removeIf(o -> o.getIp().equals(delNode.getIp()) ||
                o.getDomain().equals(delNode.getDomain()));
    }

    @Override
    public Node get(String key) {
        long hash = hash(key);
        long index = Math.abs(hash % nodes.size());
        return nodes.get((int) index);
    }
}
