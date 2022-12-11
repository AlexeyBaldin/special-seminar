package travellingsalesman.solver;

import javafx.util.Pair;
import travellingsalesman.Util;
import travellingsalesman.model.Cluster;
import travellingsalesman.model.Node;
import travellingsalesman.model.TSDataset;
import travellingsalesman.model.TSResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class TSSolverBase implements TSSolver {

    private final int clusterCount;
    private final int maxDeep;

    public TSSolverBase(int clusterCount, int maxDeep) {
        this.clusterCount = clusterCount;
        this.maxDeep = maxDeep;
    }

    protected Node findCenter(ArrayList<Node> nodes, ArrayList<Node> anotherCentres) {

        if (anotherCentres.isEmpty()) {
            return nodes.get(Util.getRandomInt(nodes.size()));
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


            return nodesWithoutCenters.get(Util.getIndexMax(lengthSum));
        }
    }

    protected ArrayList<Node> clustering(ArrayList<Node> nodes, int deep) {

        if (this.clusterCount >= nodes.size() || this.maxDeep < deep) {
            for (Node node : nodes) {
                node.setDeep(deep + 1);
            }
            return nodes;
        } else {
            ArrayList<Node> centers = new ArrayList<>();
            for (int i = 0; i < this.clusterCount; i++) {
                Node center = findCenter(nodes, centers);
                centers.add(center);
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

//                for (int i = 0; i < lengths.size(); i++) {
//                    System.out.println(node + " -> " + centers.get(i) + " : " + lengths.get(i));
//                }


                ArrayList<Integer> noChoose = new ArrayList<>();
                do {
                    int minIndex = Util.getIndexMin(lengths, noChoose);
                    Cluster cluster = (Cluster) clustersPreparation.get(minIndex);

                    if (cluster.getNodeCount() >= nodesInOneCluster) {
                        noChoose.add(minIndex);
                    } else {
                        cluster.addNode(node);
                        //System.out.println("add " + cluster.getCenter());
                        break;
                    }
                } while (true);
            });

            //clustersPreparation.forEach(System.out::println);

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
        if(nodes.get(0).isCluster()) {
            ArrayList<Cluster> clusters = new ArrayList<>();
            nodes.forEach(node -> {
                Cluster cast = (Cluster)node;
                reduction(cast);

                pathNodes(cluster.getNodes());

                clusters.add(cast);

                //Соединение вершин в ближайших кластерах
                //connectClusters(Cluster cluster1, Cluster cluster2)?
                //Получаем все вершины в обоих кластерах, соединяем две ближайшие через кластеры
                //cluster.getAllNodes()
                //Если у вершины предыдущая вершина из другого кластера, значит сначала обходим её кластер, потом из последней идем в следующий
            });
            connectClusters(clusters);
            System.out.println(clusters);

        } else {
            //Поиск пути между городами внутри последнего кластера
            //pathNodes(..)
            pathNodes(cluster.getNodes());
        }
    }

    protected void connectClusters(ArrayList<Cluster> clusters) {

        Cluster temp1 = clusters.get(0);
        Cluster temp2;

//        System.out.println("===========");
        for (int i = 0; i < clusters.size(); i++) {
            temp2 = (Cluster)temp1.getNext();

            connectTwoClusters(temp1, temp2);

            temp1 = temp2;
        }
//        System.out.println("===========");


    }

    class NodesPair extends Pair<Node, Node> {
        public NodesPair(Node key, Node value) {
            super(key, value);
        }
        public double getLength() {
            return Util.getLength(this.getKey(), this.getValue());
        }
        public void connect() {
            this.getKey().setNext(this.getValue());
            this.getValue().getPrev().setNext(null);
            this.getValue().setPrev(this.getKey());
        }
    }

    protected void connectTwoClusters(Cluster cluster1, Cluster cluster2) {

        ArrayList<NodesPair> pairs = new ArrayList<>();
        if(cluster1.getAllNodes().size() == 1) {
            for (Node node2 :
                    cluster2.getAllNodes()) {
                pairs.add(new NodesPair(cluster1.getCenter(), node2));
            }
        } else {
            for (Node node1 :
                    cluster1.getAllNodes()) {
                if(node1.isGoFromCluster()) {
                    continue;
                }
                for (Node node2 :
                        cluster2.getAllNodes()) {
                    pairs.add(new NodesPair(node1, node2));
                }
            }
        }


        int minIndex = 0;
        double minLength = pairs.get(0).getLength();
        for (int i = 1; i < pairs.size(); i++) {
            if(pairs.get(i).getLength() < minLength) {
                minIndex = i;
                minLength = pairs.get(i).getLength();
            }
        }



        pairs.get(minIndex).connect();

        //В момент соединения проверяем, не останется ли повисших вершин
        //Обозначаем вершину, в которую пришли из другого кластера как стартовую
        //Обозначаем вершину, из которой уйдём в другой кластер как финишную
        //Идём от старта к финишу
        //Если прошли все вершины кластера, то всё нормально, если не все, значит есть повисшие вершины
        //Если есть повисшие вершины, тогда инвертируем в кластере порядок обхода
    }

    protected void pathNodes(ArrayList<Node> nodes) {
        if(nodes.size() == 1) {
            nodes.get(0).setNext(nodes.get(0));
            nodes.get(0).setPrev(nodes.get(0));
        } else {

//            int random = Util.getRandomInt(nodes.size());
//            Node first = nodes.get(random);

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
        mainCluster.setNodes(clustering(nodes, 0));

        reduction(mainCluster);


        System.out.println(mainCluster.getAllNodes());
        //System.out.println(mainCluster);


        return new TSResult(null, -1);
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

