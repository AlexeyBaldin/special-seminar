package cuttingstock.solver;

import java.util.ArrayList;

public class CuttingStockAverageWithDispersionMinRemain extends CuttingStockBase {
    @Override
    protected void permutationModify(int length, ArrayList<Integer> permutation) {
        double average = 0.0;
        double dispersion = 0.0;
        for (int p :
                permutation) {
            average += p;
        }
        average /= permutation.size();


        for (int p :
                permutation) {
            dispersion += Math.abs(p - average);
        }
        dispersion /= permutation.size();


        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> middle = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        for (int p :
                permutation) {
            if(Math.abs(p - average) < dispersion) {
                middle.add(p);
            } else if(p >= average) {
                left.add(p);
            } else {
                right.add(p);
            }
        }

        ArrayList<Integer> newMiddle = new ArrayList<>();

        newMiddle.add(middle.remove(0));

        int min = length - newMiddle.get(0);
        int current = length - newMiddle.get(0);

        while (middle.size() > 0) {

            int minIndex = 0;
            boolean using = false;
            while (!using) {
                for (int i = 0; i < middle.size(); i++) {
                    if (current >= middle.get(i) && current - middle.get(i) < min) {
                        minIndex = i;
                        min = current - middle.get(i);
                        using = true;
                    }
                }
                if (using) {
                    current -= middle.get(minIndex);
                } else {
                    current = length;
                }
            }

            min = length;
            newMiddle.add(middle.remove(minIndex));
        }

        permutation.clear();
        left.sort((o1, o2) -> o2 - o1);
        permutation.addAll(left);
        //middle.sort((o1, o2) -> o2 - o1);
        permutation.addAll(newMiddle);
        right.sort((o1, o2) -> o2 - o1);
        permutation.addAll(right);
    }
}
