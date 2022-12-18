package deliveryoptimization;

import deliveryoptimization.model.DODataset;
import general.AReader;
import general.Dataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryOptimizationReader extends AReader {
    @Override
    public ArrayList<Dataset> getDatasets(String problem) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        DeliveryOptimizationReader.initFiles(problem).forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                int orders = scanner.nextInt();
                ArrayList<Integer> terms = new ArrayList<>();
                ArrayList<ArrayList<Integer>> timesMatrix = new ArrayList<>();
                for(int i = 0; i < orders; i++) {
                    terms.add(scanner.nextInt());
                }
                for(int i = 0; i <= orders; i++) {
                    for(int j = 0; j <= orders; j++) {
                        ArrayList<Integer> row = new ArrayList<>();
                        row.add(scanner.nextInt());
                        timesMatrix.add(row);
                    }
                }
                datasets.add(new DODataset(orders, terms, timesMatrix, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
