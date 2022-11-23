package cuttingstock.solver;

import java.util.ArrayList;

public class CuttingStockAverageWithDispersion extends CuttingStockBase {
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

        permutation.clear();
        left.sort((o1, o2) -> o2 - o1);
        permutation.addAll(left);
        //middle.sort((o1, o2) -> o2 - o1);
        permutation.addAll(middle);
        right.sort((o1, o2) -> o2 - o1);
        permutation.addAll(right);
    }
}
