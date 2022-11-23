package cuttingstock.solver;

import java.util.ArrayList;


public class CuttingStockBaseDescending extends CuttingStockBase {
    @Override
    protected void permutationModify(int length, ArrayList<Integer> permutation) {
        permutation.sort((o1, o2) -> o2 - o1);
    }
}
