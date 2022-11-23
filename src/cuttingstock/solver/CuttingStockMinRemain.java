package cuttingstock.solver;

import java.util.ArrayList;

public class CuttingStockMinRemain extends CuttingStockBase {
    @Override
    protected void permutationModify(int length, ArrayList<Integer> permutation) {
        ArrayList<Integer> newPermutation = new ArrayList<>();

        newPermutation.add(permutation.remove(0));

        int min = length - newPermutation.get(0);
        int current = length - newPermutation.get(0);

        while(permutation.size() > 0) {

            int minIndex = 0;
            boolean using = false;
            while(!using) {
                for (int i = 0; i < permutation.size(); i++) {
                    if(current >= permutation.get(i) && current - permutation.get(i) < min) {
                        minIndex = i;
                        min = current - permutation.get(i);
                        using = true;
                    }
                }
                if(using) {
                    current-=permutation.get(minIndex);
                } else {
                    current = length;
                }
            }

            min = length;
            newPermutation.add(permutation.remove(minIndex));
        }
        
        permutation.addAll(newPermutation);
    }

}
