package cuttingstock.task;

import cuttingstock.solver.CuttingStock;
import cuttingstock.model.CuttingStockDataset;
import cuttingstock.model.Result;

public class OneStepTask {

    private final int usingCount;
    private final double deviation;

    public OneStepTask(CuttingStockDataset dataset, CuttingStock solver) {
        Result result = solver.cuttingStock(dataset.getLength(), dataset.getPermutationCopy());

        this.usingCount = result.getUsingCount();
        this.deviation = (double)(result.getUsingCount() - dataset.getLower()) / dataset.getLower();
    }

    public int getUsingCount() {
        return usingCount;
    }

    public double getDeviation() {
        return deviation;
    }

}
