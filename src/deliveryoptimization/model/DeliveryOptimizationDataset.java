package deliveryoptimization.model;

import general.Dataset;

import java.util.Arrays;

public class DeliveryOptimizationDataset implements Dataset {

    private final int orders;
    private final int[] terms;
    private final int[][] timesMatrix;
    private final String file;

    public DeliveryOptimizationDataset(int orders, int[] terms, int[][] timesMatrix, String file) {
        this.orders = orders;
        this.terms = terms;
        this.timesMatrix = timesMatrix;
        this.file = file;
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        StringBuilder timesMatrix = new StringBuilder().append('\n');
        for(int i = 0; i <= orders; i++) {
            timesMatrix.append(Arrays.toString(this.timesMatrix[i])).append('\n');
        }
        return "Dataset " + file + " {" +
                "\norders=" + orders +
                "\nterms=" + Arrays.toString(terms) +
                "\ntimesMatrix=" + timesMatrix +
                '}';
    }
}
