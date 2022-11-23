package cuttingstock.permutation;

import java.util.ArrayList;
import java.util.Collections;

public class PartDownShufflePermutationCreator implements ShufflePermutationCreator {
    @Override
    public ArrayList<ArrayList<Integer>> createPermutations(int iterCount, int shuffleCount, ArrayList<Integer> basePermutation) {
        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();

        basePermutation.sort((o1, o2) -> o2 - o1);

        if(basePermutation.size() < iterCount) {
            iterCount = basePermutation.size();
        }

        for(int iter = 1; iter <= iterCount; iter++) {
            for(int shuffleIter = 1; shuffleIter <= shuffleCount; shuffleIter++) {

                ArrayList<Integer> permutation = new ArrayList<>();
                int pieceInPart = basePermutation.size() % iter == 0 ? basePermutation.size() / iter : basePermutation.size() / iter + 1;
                int k = 0;
                for(int i = 0; i < iter; i++) {
                    ArrayList<Integer> part = new ArrayList<>();
                    for(int j = 0; j < pieceInPart; j++) {
                        if(k < basePermutation.size()) {
                            part.add(basePermutation.get(k++));
                        }
                    }

                    if(i%2 == 0) {
                        Collections.shuffle(part);
                    }

                    permutation.addAll(part);
                }

                permutations.add(permutation);

            }
        }
        return permutations;
    }

}
