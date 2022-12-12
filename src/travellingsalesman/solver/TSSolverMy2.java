package travellingsalesman.solver;

import travellingsalesman.Util;
import travellingsalesman.model.Cluster;
import travellingsalesman.model.Node;

import java.util.ArrayList;

public class TSSolverMy2 extends TSSolverBase{
    public TSSolverMy2(int clusterCount, int maxDeep, int reductionStopper) {
        super(clusterCount, maxDeep, reductionStopper);
    }


    protected ArrayList<Node> clustering(ArrayList<Node> nodes, int deep) {

        if (this.reductionStopper > nodes.size() || this.maxDeep < deep) {
            for (Node node : nodes) {
                node.setDeep(deep + 1);
            }
            return nodes;
        } else {
            ArrayList<Node> centers = new ArrayList<>();
            while(centers.size() < this.clusterCount) {
                findCenter(nodes, centers);
            }

            ArrayList<Node> clustersPreparation = new ArrayList<>();
            int nodesInOneCluster = nodes.size() % this.clusterCount == 0 ? nodes.size() / this.clusterCount : nodes.size() / this.clusterCount + 1;

            for (int i = 0; i < this.clusterCount; i++) {
                clustersPreparation.add(new Cluster(centers.get(i)));
            }

            ArrayList<Node> nodesWithoutCenters = new ArrayList<>(nodes);
            centers.forEach(nodesWithoutCenters::remove);
//            nodesWithoutCenters.forEach(System.out::println);
            nodesWithoutCenters.forEach(node -> {
                ArrayList<Double> lengths = new ArrayList<>();

                for (int i = 0; i < centers.size(); i++) {
                    Node center = centers.get(i);
                    lengths.add(Math.sqrt(Math.pow(center.getX() - node.getX(), 2) + Math.pow(center.getY() - node.getY(), 2)));
                }

                int minIndex = Util.getIndexMin(lengths);
                Cluster cluster = (Cluster) clustersPreparation.get(minIndex);
                cluster.addNode(node);
            });


            ArrayList<Node> clusters = new ArrayList<>();

            clustersPreparation.forEach(clusterPreparation -> {
                Cluster clusterP = (Cluster) clusterPreparation;
                ArrayList<Node> nodes1 = clusterP.getNodes();

                ArrayList<Node> clustering = clustering(nodes1, deep + 1);

                Cluster cluster = new Cluster(clusterP.getCenter());
                cluster.setNodes(clustering);
                cluster.setDeep(deep + 1);
                cluster.findGravity();
                clusters.add(cluster);
            });

            return clusters;
        }
    }
}
