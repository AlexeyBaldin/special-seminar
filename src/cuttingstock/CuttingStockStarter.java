package cuttingstock;

import cuttingstock.model.CuttingStockDataset;
import cuttingstock.permutation.PartDownShufflePermutationCreator;
import cuttingstock.solver.CuttingStockAverageWithDispersionMinRemain;
import cuttingstock.solver.CuttingStockBase;
import cuttingstock.solver.CuttingStockBaseDescending;
import cuttingstock.task.IterationTask;
import cuttingstock.task.OneStepTask;
import general.Dataset;
import general.Starter;

import java.util.ArrayList;

public class CuttingStockStarter implements Starter {

    private final ArrayList<CuttingStockDataset> datasets = new ArrayList<>();

    public CuttingStockStarter() {
        ArrayList<Dataset> cuttingStockDatasets = new CuttingStockReader().getDatasets("cuttingstock");
        cuttingStockDatasets.forEach(c -> this.datasets.add((CuttingStockDataset) c));
    }

    public void start() {
        oneStep();
        iterative(10, 50);
    }

    private void oneStep() {
        double averageDeviationBaseDescending = 0;
        double averageDeviationMyOneStep = 0;

        for (CuttingStockDataset dataset : datasets) {
            OneStepTask baseOneStep = new OneStepTask(dataset, new CuttingStockBaseDescending());
            OneStepTask myOneStep = new OneStepTask(dataset, new CuttingStockAverageWithDispersionMinRemain());
            double deviationBase = baseOneStep.getDeviation();
            double deviationMy = myOneStep.getDeviation();
            System.out.println(dataset.getFile());
            System.out.println("    Lower = " + dataset.getLower() + " | baseOneStep = " + baseOneStep.getUsingCount()
                    + ", deviation = " + deviationBase + " | myOneStep = " + myOneStep.getUsingCount()
                    + ", deviation = " + deviationMy);
            averageDeviationBaseDescending += deviationBase;
            averageDeviationMyOneStep += deviationMy;
        }
        averageDeviationBaseDescending /= datasets.size();
        averageDeviationMyOneStep /= datasets.size();
        System.out.println("Average deviation base = " + averageDeviationBaseDescending);
        System.out.println("Average deviation myOneStep = " + averageDeviationMyOneStep);
    }

    private void iterative(int iterCount, int shuffleCount) {

        for (CuttingStockDataset dataset : datasets) {
//            IterationTask iterationTask = new IterationTask(iterCount, dataset,
//                    new CuttingStockBase(), new PartDownPermutationCreator());

            IterationTask iterationTask = new IterationTask(iterCount, shuffleCount, dataset,
                    new CuttingStockBase(), new PartDownShufflePermutationCreator());

            OneStepTask baseOneStep = new OneStepTask(dataset, new CuttingStockBaseDescending());

            System.out.println(dataset.getFile());
            System.out.println("    Lower = " + dataset.getLower() +
                    " | baseOneStepSources = " + baseOneStep.getUsingCount() +
                    " | recordSources = " + iterationTask.getRecordSources() +
                    ", recordIter = " + iterationTask.getRecordIter() +
                    ", recordDeviation = " + iterationTask.getRecordDeviation() +
                    ", deviation from base = " + (baseOneStep.getUsingCount() - iterationTask.getRecordSources()));
        }
    }


}
