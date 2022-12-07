package travellingsalesman;

import general.AReader;
import general.Dataset;
import orderselection.model.OrderSelectionDataset;
import travellingsalesman.model.TravellingSalesmanDataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TravellingSalesmanReader  extends AReader {
    @Override
    public ArrayList<Dataset> getDatasets(String problem) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        initFiles(problem).forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                int optimum = scanner.nextInt();
                String string;
                do {
                    string = scanner.nextLine();
                } while(!string.startsWith("DIMENSION"));
                int size = Integer.parseInt(string.substring(12));
                do {
                    string = scanner.nextLine();
                } while(!string.startsWith("NODE_COORD_SECTION"));
                ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
                do {
                    string = scanner.nextLine();
                    if("EOF".equals(string)) {
                        break;
                    }
                    String[] split = string.split(" ");
                    ArrayList<Double> row = new ArrayList<>(2);
                    row.add(Double.parseDouble(split[1]));
                    row.add(Double.parseDouble(split[2]));
                    coordinates.add(row);
                } while(true);

                datasets.add(new TravellingSalesmanDataset(size, coordinates, optimum, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
