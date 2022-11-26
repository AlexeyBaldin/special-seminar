package deliveryoptimization;

import deliveryoptimization.model.DeliveryOptimizationDataset;
import general.Dataset;
import general.Starter;

import java.util.ArrayList;

public class DeliveryOptimizationStarter implements Starter {

    ArrayList<DeliveryOptimizationDataset> datasets = new ArrayList<>();

    public DeliveryOptimizationStarter() {
        ArrayList<Dataset> deliveryOptimizationDatasets = new DeliveryOptimizationReader().getDatasets("deliveryoptimization");
        deliveryOptimizationDatasets.forEach(o -> datasets.add((DeliveryOptimizationDataset) o));
    }

    @Override
    public void start() {

    }
}
