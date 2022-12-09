package travellingsalesman.model;

import java.util.ArrayList;

public class Cluster extends Node {

    private ArrayList<Node> nodes = new ArrayList<>();
    private Node center;

    public Cluster(Node center) {
        super();
        this.center = center;
        nodes.add(center);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getCenter() {
        return center;
    }

    public void setCenter(Node center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "Cluster{count=" + nodes.size() +
                ", nodes=" + nodes +
                '}';
    }
}
