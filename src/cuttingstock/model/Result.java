package cuttingstock.model;

import java.util.ArrayList;

public class Result {

    private final int usingCount;
    private final ArrayList<Integer> sourceNumber;


    public Result(int usingCount, ArrayList<Integer> sourceNumber) {
        this.usingCount = usingCount;
        this.sourceNumber = sourceNumber;
    }

    public int getUsingCount() {
        return usingCount;
    }

    public ArrayList<Integer> getSourceNumber() {
        return sourceNumber;
    }

    @Override
    public String toString() {
        return "model.CuttingStockResult{" +
                "usingCount=" + usingCount +
                ", sourceNumber=" + sourceNumber +
                '}';
    }
}
