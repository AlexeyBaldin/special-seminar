package cuttingstock.task;

import cuttingstock.solver.CuttingStock;
import cuttingstock.model.CuttingStockDataset;
import cuttingstock.model.Result;
import cuttingstock.permutation.PermutationCreator;
import cuttingstock.permutation.ShufflePermutationCreator;

import java.util.ArrayList;

public class IterationTask {

    private int recordSources;
    private int recordIter;
    private double recordDeviation;

    public IterationTask(int iterCount, CuttingStockDataset dataset, CuttingStock solver, PermutationCreator permutationCreator) {
        ArrayList<ArrayList<Integer>> permutations = permutationCreator
                .createPermutations(iterCount, dataset.getPermutationCopy());

        recordIter = 0;
        recordSources = 0;
        recordDeviation = 0.0;
        int iter = 0;

        for(ArrayList<Integer> permutation: permutations) {
            Result result = solver.cuttingStock(dataset.getLength(), permutation);

            if(iter == 0) {
                recordSources = result.getUsingCount();
                recordDeviation = (double)(result.getUsingCount() - dataset.getLower()) / dataset.getLower();
            } else if(recordSources > result.getUsingCount()) {
                recordIter = iter;
                recordSources = result.getUsingCount();
                recordDeviation = (double)(result.getUsingCount() - dataset.getLower()) / dataset.getLower();
            }

            iter++;
        }
    }

    public IterationTask(int iterCount, int shuffleCount, CuttingStockDataset dataset, CuttingStock solver, ShufflePermutationCreator shufflePermutationCreator) {

        ArrayList<ArrayList<Integer>> permutations = shufflePermutationCreator
                .createPermutations(iterCount, shuffleCount, dataset.getPermutationCopy());

        //System.out.println(permutations.size());

        recordIter = 0;
        recordSources = 0;
        recordDeviation = 0.0;
        int iter = 0;

        for(ArrayList<Integer> permutation: permutations) {
            Result result = solver.cuttingStock(dataset.getLength(), permutation);

            if(iter == 0) {
                recordSources = result.getUsingCount();
                recordDeviation = (double)(result.getUsingCount() - dataset.getLower()) / dataset.getLower();
            } else if(recordSources > result.getUsingCount()) {
                recordIter = iter/shuffleCount;
                recordSources = result.getUsingCount();
                recordDeviation = (double)(result.getUsingCount() - dataset.getLower()) / dataset.getLower();
            }

            iter++;
        }
    }

    public int getRecordSources() {
        return recordSources;
    }

    public int getRecordIter() {
        return recordIter;
    }

    public double getRecordDeviation() {
        return recordDeviation;
    }
}
