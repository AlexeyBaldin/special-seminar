package deliveryoptimization;

import deliveryoptimization.model.DeliveryOptimizationDataset;
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
                int[] terms = new int[orders];
                int[][] timesMatrix = new int[orders + 1][orders + 1];
                for(int i = 0; i < orders; i++) {
                    terms[i] = scanner.nextInt();
                }
                for(int i = 0; i <= orders; i++) {
                    for(int j = 0; j <= orders; j++) {
                        timesMatrix[i][j] = scanner.nextInt();
                    }
                }
                datasets.add(new DeliveryOptimizationDataset(orders, terms, timesMatrix, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
