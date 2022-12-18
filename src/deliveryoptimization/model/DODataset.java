package deliveryoptimization.model;

import general.Dataset;

import java.util.ArrayList;
import java.util.Arrays;

public class DODataset implements Dataset {

    private final int orders;
    private final ArrayList<Integer> terms;
    private final ArrayList<ArrayList<Integer>> timesMatrix;
    private final String file;

    public DODataset(int orders, ArrayList<Integer> terms, ArrayList<ArrayList<Integer>> timesMatrix, String file) {
        this.orders = orders;
        this.terms = terms;
        this.timesMatrix = timesMatrix;
        this.file = file;
    }

    public int getOrders() {
        return orders;
    }

    public ArrayList<Integer> getTerms() {
        return terms;
    }

    public ArrayList<ArrayList<Integer>> getTimesMatrix() {
        return timesMatrix;
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "DODataset{" +
                "orders=" + orders +
                ", terms=" + terms +
                ", timesMatrix=" + timesMatrix +
                ", file='" + file + '\'' +
                '}';
    }
}
