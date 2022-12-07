package travellingsalesman;

import deliveryoptimization.DeliveryOptimizationReader;
import deliveryoptimization.model.DeliveryOptimizationDataset;
import general.Dataset;
import general.Starter;
import travellingsalesman.model.TravellingSalesmanDataset;

import java.util.ArrayList;

public class TravellingSalesmanStarter implements Starter {

    ArrayList<TravellingSalesmanDataset> datasets = new ArrayList<>();

    public TravellingSalesmanStarter() {
        ArrayList<Dataset> deliveryOptimizationDatasets = new TravellingSalesmanReader().getDatasets("travellingsalesman");
        deliveryOptimizationDatasets.forEach(o -> datasets.add((TravellingSalesmanDataset) o));
    }

    @Override
    public void start() {
        datasets.forEach(dataset -> {
            System.out.println(dataset.getFile());

        });
    }
}
