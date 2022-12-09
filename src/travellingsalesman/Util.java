package travellingsalesman;

import travellingsalesman.model.Node;

import java.util.ArrayList;

public class Util {

    public static double getLength(Node a, Node b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public static int getIndexMax(ArrayList<Double> doubles) {
        int maxIndex = 0;
        double max = 0;
        for (int i = 0; i < doubles.size(); i++) {
            if(max < doubles.get(i)) {
                max = doubles.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int getIndexMax(ArrayList<Double> doubles, ArrayList<Integer> noChoose) {
        int maxIndex = 0;
        double max = 0;
        for (int i = 0; i < doubles.size(); i++) {
            boolean choosing = true;
            for (Integer integer : noChoose) {
                choosing = choosing && integer != i;
            }
            if(max < doubles.get(i) && choosing) {
                max = doubles.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
