package travellingsalesman.solver;

import travellingsalesman.Util;
import travellingsalesman.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TSSolverBase implements TSSolver {

    protected final int clusterCount;
    protected final int maxDeep;
    protected final int reductionStopper;

    public TSSolverBase(int clusterCount, int maxDeep, int reductionStopper) {
        this.clusterCount = clusterCount;
        this.maxDeep = maxDeep;
        this.reductionStopper = reductionStopper;
    }

    protected void findCenter(ArrayList<Node> nodes, ArrayList<Node> anotherCentres) {

        if (anotherCentres.isEmpty()) {
            //return nodes.get(Util.getRandomInt(nodes.size()));
            Node firstCenter = nodes.get(0);
            Node secondCenter = nodes.get(1);

            double maxLength = 0;
            for (Node node1 :
                    nodes) {
                for (Node node2:
                     nodes) {
                    double length = Util.getLength(node1, node2);
                    if(length > maxLength) {
                        firstCenter = node1;
                        secondCenter = node2;
                    }
                }
            }


            anotherCentres.add(firstCenter);
            anotherCentres.add(secondCenter);
        } else {
            ArrayList<Double> lengthSum = new ArrayList<>();
            ArrayList<Node> nodesWithoutCenters = new ArrayList<>(nodes);
            anotherCentres.forEach(nodesWithoutCenters::remove);

            nodesWithoutCenters.forEach(node -> lengthSum.add(0.0));

            anotherCentres.forEach(anotherCenter -> {
                for (int i = 0; i < nodesWithoutCenters.size(); i++) {
                    Node node = nodesWithoutCenters.get(i);
                    double length = Math.sqrt(Math.pow(anotherCenter.getX() - node.getX(), 2) + Math.pow(anotherCenter.getY() - node.getY(), 2));
                    lengthSum.set(i, lengthSum.get(i) + length);

                    //System.out.println(anotherCenter.getNumber() + " -> " + node.getNumber() + " : " + length);
                }
            });


            anotherCentres.add(nodesWithoutCenters.get(Util.getIndexMax(lengthSum)));
        }
    }

    protected ArrayList<Node> clustering(ArrayList<Node> nodes, int deep) {

        if (this.reductionStopper > nodes.size() || this.maxDeep < deep) {
            for (Node node : nodes) {
                node.setDeep(deep + 1);
            }
            return nodes;
        } else {
            ArrayList<Node> centers = new ArrayList<>();

            while(centers.size() < this.clusterCount && nodes.size() > centers.size()) {
                findCenter(nodes, centers);
            }


            ArrayList<Node> clustersPreparation = new ArrayList<>();
            int nodesInOneCluster = nodes.size() % centers.size() == 0 ? nodes.size() / centers.size() : nodes.size() / centers.size() + 1;

            for (int i = 0; i < centers.size(); i++) {
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

                ArrayList<Integer> noChoose = new ArrayList<>();
                do {
                    int minIndex = Util.getIndexMin(lengths, noChoose);
                    Cluster cluster = (Cluster) clustersPreparation.get(minIndex);

                    if (cluster.getNodeCount() >= nodesInOneCluster) {
                        noChoose.add(minIndex);
                    } else {
                        cluster.addNode(node);
                        break;
                    }
                } while (true);
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


    protected void reduction(Cluster cluster) {
        ArrayList<Node> nodes = cluster.getNodes();
        if (nodes.get(0).isCluster()) {
            ArrayList<Cluster> clusters = new ArrayList<>();
            nodes.forEach(node -> {
                Cluster cast = (Cluster) node;

                //Соединяем кластеры как вершины
                pathNodes(cluster.getNodes());
                //System.out.println(cluster);

                //Спускаемся на следующий уровень
                reduction(cast);


                clusters.add(cast);

                //Соединение вершин в ближайших кластерах
                //connectClusters(Cluster cluster1, Cluster cluster2)?
                //Получаем все вершины в обоих кластерах, соединяем две ближайшие через кластеры
                //cluster.getAllNodes()
                //Если у вершины предыдущая вершина из другого кластера, значит сначала обходим её кластер, потом из последней идем в следующий
            });


            connectClusters(clusters);
//            System.out.println("============");
//            System.out.println(cluster);
//            System.out.println("============");

        } else {
            //Поиск пути между городами внутри последнего кластера
            //pathNodes(..)


            pathNodes(cluster.getNodes());
        }
    }

    protected void connectClusters(ArrayList<Cluster> clusters) {
        Cluster temp1 = clusters.get(0);
        Cluster temp2;

        //System.out.println("===========");
        for (int i = 1; i < clusters.size(); i++) {
            temp2 = (Cluster) temp1.getNext();

            connectTwoClusters(temp1, temp2);
            //System.out.println("temp1" + temp1 + "  temp2" + temp2);

            temp1 = temp2;
        }
        //System.out.println("===========");
    }

    protected void connectTwoClusters(Cluster cluster1, Cluster cluster2) {

        ArrayList<NodesPair> pairs = new ArrayList<>();
        if (cluster1.getAllNodes().size() == 1) {
            for (Node node2 :
                    cluster2.getAllNodes()) {
                pairs.add(new NodesPair(cluster1.getCenter(), node2));
            }
        } else {
            for (Node node1 :
                    cluster1.getAllNodes()) {
                if (node1.isFinish()) {
                    continue;
                }
                for (Node node2 :
                        cluster2.getAllNodes()) {
                    pairs.add(new NodesPair(node1, node2));
                }
            }
        }

        NodesPair nearestPair1 = findNearestPair(pairs);
        Node keyKey = nearestPair1.getKey().getPrev();
        Node keyValue = nearestPair1.getKey().getNext();
        Node valueKey = nearestPair1.getValue().getPrev();
        Node valueValue = nearestPair1.getValue().getNext();

        pairs.clear();
        pairs.add(new NodesPair(keyKey, valueKey));
        pairs.add(new NodesPair(keyKey, valueValue));
        pairs.add(new NodesPair(keyValue, valueKey));
        pairs.add(new NodesPair(keyValue, valueKey));

        NodesPair nearestPair2 = findNearestPair(pairs);

        connectPairs(nearestPair1, nearestPair2);

    }

    protected void connectPairs(NodesPair pair1, NodesPair pair2) {
        if(pair1.getKey().getNext().equals(pair2.getKey())) {
            if(pair1.getValue().getNext().equals(pair2.getValue())) {
                pair1.getValue().invert();
            }
        } else {
            if(pair1.getValue().getNext().equals(pair2.getValue())) {
                pair1.getKey().invert();
                pair1.getValue().invert();
            } else {
                pair1.getKey().invert();
            }
        }

        pair1.getKey().setNext(pair1.getValue());
        pair1.getValue().setPrev(pair1.getKey());

        pair2.getValue().setNext(pair2.getKey());
        pair2.getKey().setPrev(pair2.getValue());
    }

    protected NodesPair findNearestPair(ArrayList<NodesPair> pairs) {
        int minIndex = 0;
        double minLength = pairs.get(0).getLength();
        for (int i = 1; i < pairs.size(); i++) {
            if (pairs.get(i).getLength() < minLength) {
                minIndex = i;
                minLength = pairs.get(i).getLength();
            }
        }
        return pairs.get(minIndex);
    }

    protected void pathNodes(ArrayList<Node> nodes) {
        if (nodes.size() == 1) {
            nodes.get(0).setNext(nodes.get(0));
            nodes.get(0).setPrev(nodes.get(0));
        } else {

            Node first = nodes.get(0);
            ArrayList<Node> tempNodes = new ArrayList<>(nodes);
            tempNodes.remove(first);
            int size = tempNodes.size();
            Node temp1 = first;
            Node temp2 = null;
            for (int i = 0; i < size; i++) {
                temp2 = findNearest(temp1, tempNodes);
                temp1.setNext(temp2);
                temp2.setPrev(temp1);
                temp1 = temp2;
                tempNodes.remove(temp1);
            }

            if (temp2 != null) {
                temp2.setNext(first);
                first.setPrev(temp2);
            }
        }
    }

    protected Node findNearest(Node node, ArrayList<Node> anotherNodes) {
        ArrayList<Double> lengths = new ArrayList<>();
        for (Node value : anotherNodes) {
            lengths.add(Util.getLength(node, value));
        }
        return anotherNodes.get(Util.getIndexMin(lengths));
    }

    @Override
    public TSResult solve(TSDataset dataset) {

        ArrayList<Node> nodes = new ArrayList<>();

        dataset.getCoordinates().forEach(coordinates -> nodes.add(new Node(coordinates.getNumber(), coordinates.getX(), coordinates.getY())));


        Cluster mainCluster = new Cluster(null);
        mainCluster.setNodes(clustering(nodes, 1));

        reduction(mainCluster);


        //System.out.println(mainCluster.getAllNodes());
        ArrayList<Node> allNodes = mainCluster.getAllNodes();
        Node next1 = allNodes.get(0);
        Node next2;
        ArrayList<Integer> test = new ArrayList<>();
        for (int i = 0; i < allNodes.size(); i++) {
            test.add(0);
        }

        ArrayList<Long> order = new ArrayList<>();
        int length = 0;
        for (int i = 0; i < allNodes.size(); i++) {
            test.set((int) next1.getNumber() - 1, 5);
            next2 = next1.getNext();
            length += Util.getLength(next1, next2);
            //System.out.println(next1.getNumber() + " -> " + next2.getNumber() + " = " + Util.getLength(next1, next2));
            order.add(next1.getNumber());
            next1 = next2;
        }
        //System.out.println(test);
//        System.out.println(length);
        //System.out.println(mainCluster);

        return new TSResult(order, length);
    }
}

class MyFrame extends JFrame {

    private final Canvas canvas;

    public MyFrame() {
        setSize(1200, 1200);
        this.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        this.canvas = canvas;
        this.canvas.setSize(1200, 1200);
        this.add(canvas);
        setVisible(true);
    }

    public void paintPoint(ArrayList<Node> nodes, Node center) {
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();
        if (s.equals("q")) {
            Graphics graphics = this.canvas.getGraphics();
            graphics.setColor(Color.BLACK);
            nodes.forEach(node -> {
//                System.out.println(node.getX()/50 + " " + node.getY()/50);
                graphics.drawOval(((int) node.getX() / 50), ((int) node.getY() / 50), 3, 3);
            });
            graphics.fillRect(((int) center.getX() / 50), ((int) center.getY() / 50), 3, 3);
        }
        if (s.equals("w")) {
            Graphics graphics = this.canvas.getGraphics();
            graphics.setColor(Color.GREEN);
            nodes.forEach(node -> {
//                System.out.println(node.getX()/50 + " " + node.getY()/50);
                graphics.drawOval(((int) node.getX() / 50), ((int) node.getY() / 50), 3, 3);
            });
            graphics.fillRect(((int) center.getX() / 50), ((int) center.getY() / 50), 3, 3);
        }
        if (s.equals("e")) {
            Graphics graphics = this.canvas.getGraphics();
            graphics.setColor(Color.BLUE);
            nodes.forEach(node -> {
//                System.out.println(node.getX()/50 + " " + node.getY()/50);
                graphics.drawOval(((int) node.getX() / 50), ((int) node.getY() / 50), 3, 3);
            });
            graphics.fillRect(((int) center.getX() / 50), ((int) center.getY() / 50), 3, 3);
        }
        if (s.equals("r")) {
            Graphics graphics = this.canvas.getGraphics();
            graphics.setColor(Color.RED);
            nodes.forEach(node -> {
//                System.out.println(node.getX()/50 + " " + node.getY()/50);
                graphics.drawOval(((int) node.getX() / 50), ((int) node.getY() / 50), 3, 3);
            });
            graphics.fillRect(((int) center.getX() / 50), ((int) center.getY() / 50), 3, 3);

        }


//        System.out.println("qwe");
    }
}

