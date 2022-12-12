package travellingsalesman.model;

import java.util.ArrayList;
import java.util.Collection;

public class Cluster extends Node {

    private ArrayList<Node> nodes = new ArrayList<>();
    private Node center;

    public void findGravity() {
        for (Node node : nodes) {
            this.x += node.x;
            this.y += node.y;
        }

        this.x /= nodes.size();
        this.y /= nodes.size();
    }

    public void invertCluster() {
        getAllNodes().forEach(node -> {
            Node temp = node.next;
            node.next = node.prev;
            node.prev = temp;
        });
    }

    public Cluster(Node center) {
        super();
        this.center = center;
        nodes.add(center);
    }

    @Override
    public boolean isCluster() {
        return true;
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

    public long getNumber() {
        return center.number;
    }



    @Override
    public String toString() {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < deep; i++) {
            spaces.append("  ");
        }
        if(prev != null && next != null) {
            return "\n" + spaces + "Cluster{number=" + center.number + ", size=" + nodes.size() + ", x=" + x + ", y=" + y +
                    ", prev=" + prev.getNumber() + ", next=" + next.getNumber() +
                    ", nodes=" + nodes +
                    '}';
        } else {
            return "\n" + spaces + "Cluster{size=" + nodes.size() + ", x=" + x + ", y=" + y +
                    ", prev=-1, next=-1" +
                    ", nodes=" + nodes +
                    '}';
        }

    }

    public ArrayList<Node> getAllNodes() {
        ArrayList<Node> allNodes = new ArrayList<>();

        if(this.nodes.get(0).isCluster()) {
            this.nodes.forEach(cluster -> {
                allNodes.addAll(((Cluster)cluster).getAllNodes());
            });
        } else {
            allNodes.addAll(this.nodes);
        }

        return allNodes;
    }
}
