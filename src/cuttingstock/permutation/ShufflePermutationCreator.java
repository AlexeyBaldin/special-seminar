package cuttingstock.permutation;

import java.util.ArrayList;

public interface ShufflePermutationCreator {
    ArrayList<ArrayList<Integer>> createPermutations(int iterCount, int shuffleCount, ArrayList<Integer> basePermutation);
}
