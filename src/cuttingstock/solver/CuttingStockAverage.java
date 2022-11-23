package cuttingstock.solver;

import java.util.ArrayList;

public class CuttingStockAverage extends CuttingStockBase {
    @Override
    protected void permutationModify(int length, ArrayList<Integer> permutation) {

        double average = 0.0;
        for (int p :
                permutation) {
            average += p;
        }
        average /= permutation.size();


        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        for (int p :
                permutation) {
            if (p < average) {
                right.add(p);
            } else {
                left.add(p);
            }
        }

        left.sort((o1, o2) -> o2 - o1);
        //Collections.shuffle(right);

        permutation.clear();
        permutation.addAll(left);
        permutation.addAll(right);
    }

    @Override
    protected int choosingStrategy(int length, int current, ArrayList<Integer> permutation, ArrayList<Integer> used) {
        int using = -1;

        for (int j = 0; j < used.size(); j++) {
            if (used.get(j) >= permutation.get(current)) {
                using = j;
                break;
            }
        }

        return using;
    }
}
