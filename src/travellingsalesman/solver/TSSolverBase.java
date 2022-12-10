package travellingsalesman.solver;

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


    private void reduction(Cluster cluster) {
        ArrayList<Node> nodes = cluster.getNodes();
        if(nodes.get(0).isCluster()) {
            nodes.forEach(node -> {
                Cluster cast = (Cluster)node;
                reduction(cast);

                //Соединение кластеров внутри кластера
                //pathNodes(..)
                pathNodes(cluster.getNodes());

                //Соединение вершин в ближайших кластерах
                //connectClusters(Cluster cluster1, Cluster cluster2)?
                //Получаем все вершины в обоих кластерах, соединяем две ближайшие через кластеры
                //cluster.getAllNodes()
                //Если у вершины предыдущая вершина из другого кластера, значит сначала обходим её кластер, потом из последней идем в следующий
            });

        } else {
            //Поиск пути между городами внутри последнего кластера
            //pathNodes(..)
            pathNodes(cluster.getNodes());
        }
    }

    private void pathNodes(ArrayList<Node> nodes) {
        if(nodes.size() == 1) {
            nodes.get(0).setNext(nodes.get(0));
            nodes.get(0).setPrev(nodes.get(0));
        } else {
            int random = Util.getRandomInt(nodes.size());

            Node first = nodes.get(random);
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


            //NEXT GOAL
            //Соединять кластеры как вершины
            //в двух соединенных кластерах брать ближайшие вершины и соединять

        }
    }

    private Node findNearest(Node node, ArrayList<Node> nodes) {
        ArrayList<Double> lengths = new ArrayList<>();
        for (Node value : nodes) {
            lengths.add(Util.getLength(node, value));
        }
        return nodes.get(Util.getIndexMin(lengths));
    }

    @Override
    public TSResult solve(TSDataset dataset) {

        ArrayList<Node> nodes = new ArrayList<>();

        dataset.getCoordinates().forEach(coordinates -> nodes.add(new Node(coordinates.getNumber(), coordinates.getX(), coordinates.getY())));


        Cluster mainCluster = new Cluster(null);
        mainCluster.setNodes(clustering(nodes, 0));
        System.out.println(mainCluster);

        reduction(mainCluster);

        System.out.println(mainCluster);


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

