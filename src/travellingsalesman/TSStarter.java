package travellingsalesman;

import general.Dataset;
import general.Starter;
import travellingsalesman.model.TSDataset;
import travellingsalesman.solver.TSSolverBase;
import travellingsalesman.task.TSTask;

import java.util.ArrayList;

public class TSStarter implements Starter {

    ArrayList<TSDataset> datasets = new ArrayList<>();

    public TSStarter() {
        ArrayList<Dataset> deliveryOptimizationDatasets = new TSReader().getDatasets("travellingsalesman");
        deliveryOptimizationDatasets.forEach(o -> datasets.add((TSDataset) o));
    }

    @Override
    public void start() {
//        TSDataset dataset = datasets.get(0);
//        TSTask task = new TSTask(dataset, new TSSolverBase(4, 32));


        datasets.forEach(dataset -> {
            System.out.println(dataset.getFile());
            TSTask task = new TSTask(dataset, new TSSolverBase(2, 4));
        });
    }
}
