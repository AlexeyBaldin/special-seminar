package travellingsalesman;

import general.AReader;
import general.Dataset;
import travellingsalesman.model.Coordinates;
import travellingsalesman.model.TSDataset;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TSReader extends AReader {
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
                ArrayList<Coordinates> coordinates = new ArrayList<>();
                do {
                    string = scanner.nextLine();
                    if("EOF".equals(string)) {
                        break;
                    }
                    String[] split = string.split(" ");
                    coordinates.add(new Coordinates(Integer.parseInt(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[1])));
                } while(true);

                datasets.add(new TSDataset(size, coordinates, optimum, file.toString()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return datasets;
    }
}
