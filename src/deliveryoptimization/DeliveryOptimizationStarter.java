package deliveryoptimization;

import deliveryoptimization.model.DODataset;
import deliveryoptimization.model.DOResult;
import deliveryoptimization.solver.SolverBase;
import general.Dataset;
import general.Starter;

import java.util.ArrayList;

public class DeliveryOptimizationStarter implements Starter {

    ArrayList<DODataset> datasets = new ArrayList<>();

    public DeliveryOptimizationStarter() {
        ArrayList<Dataset> deliveryOptimizationDatasets = new DeliveryOptimizationReader().getDatasets("deliveryoptimization");
        deliveryOptimizationDatasets.forEach(o -> datasets.add((DODataset) o));
    }

    @Override
    public void start() {
        DODataset dataset = datasets.get(0);

        SolverBase solverBase = new SolverBase(dataset);
        DOResult resultBase = solverBase.solve();

    }
}
