package cuttingstock.solver;

import cuttingstock.model.Result;

import java.util.ArrayList;

public class CuttingStockBase implements CuttingStock {

    protected void permutationModify(int length, ArrayList<Integer> permutation){
        //No modify
    }

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

    public final Result cuttingStock(int length, ArrayList<Integer> permutation) {
        ArrayList<Integer> used = new ArrayList<>();
        ArrayList<Integer> sourceNumber = new ArrayList<>();

        permutationModify(length, permutation);

        int source = 1;
        sourceNumber.add(source);
        used.add(length - permutation.get(0));

        for (int i = 1; i < permutation.size(); i++) {
            int using = choosingStrategy(length, i, permutation, used);

            if(using >= 0 && using < used.size() && used.get(using) >= permutation.get(i)) {
                used.set(using, used.get(using) - permutation.get(i));
                sourceNumber.add(using+1);
            } else {
                used.add(length - permutation.get(i));
                source++;
                sourceNumber.add(source);
            }
        }
        return new Result(source, sourceNumber);
    }
}
