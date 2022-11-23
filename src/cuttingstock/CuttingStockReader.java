package cuttingstock;

import cuttingstock.model.CuttingStockDataset;
import general.AReader;
import general.Dataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CuttingStockReader extends AReader {
    public ArrayList<Dataset> getDatasets(String problem) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        initFiles(problem).forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                int length = scanner.nextInt();
                ArrayList<Integer> permutation = new ArrayList<>();
                while(scanner.hasNext()) {
                    permutation.add(scanner.nextInt());
                }
                datasets.add(new CuttingStockDataset(length, permutation, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
