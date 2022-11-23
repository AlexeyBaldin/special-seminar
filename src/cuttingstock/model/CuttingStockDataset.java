package cuttingstock.model;

import general.Dataset;

import java.util.ArrayList;

public class CuttingStockDataset implements Dataset {

    private final int length;
    private final ArrayList<Integer> permutation;
    private final String file;

    private final int lower;

    public CuttingStockDataset(int length, ArrayList<Integer> permutation, String file) {
        this.length = length;
        this.permutation = permutation;
        this.file = file;

        int sum = 0;
        for (int piece :
                permutation) {
            sum+=piece;
        }

        this.lower = sum % length == 0 ? sum/length : sum/length + 1;
    }

    public String getFile() {
        return file;
    }

    public int getLength() {
        return length;
    }

    public int getLower() {
        return lower;
    }

    public ArrayList<Integer> getPermutationCopy() {
        return new ArrayList<>(permutation);
    }

    @Override
    public String toString() {
        return "model.general.Dataset{" +
                "length=" + length +
                ", permutation=" + permutation +
                '}';
    }
}
