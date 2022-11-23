package cuttingstock.permutation;

import java.util.ArrayList;

public interface PermutationCreator {
    ArrayList<ArrayList<Integer>> createPermutations(int iterCount, ArrayList<Integer> basePermutation);
}
