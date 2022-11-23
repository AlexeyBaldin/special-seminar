package orderselection;

import general.AReader;
import general.Dataset;
import orderselection.model.OrderSelectionDataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderSelectionReader extends AReader {
    @Override
    public ArrayList<Dataset> getDatasets(String problem) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        initFiles(problem).forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                int performance = scanner.nextInt();
                int count = scanner.nextInt();
                ArrayList<Integer> cost = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    cost.add(scanner.nextInt());
                }
                ArrayList<Integer> income = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    income.add(scanner.nextInt());
                }
                datasets.add(new OrderSelectionDataset(performance, count, cost, income, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
