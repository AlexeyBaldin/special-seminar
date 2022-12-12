package travellingsalesman;

import general.Dataset;
import general.Starter;
import travellingsalesman.model.TSDataset;
import travellingsalesman.solver.TSSolverBase;
import travellingsalesman.solver.TSSolverMy;
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


        double average = 0;
        for (TSDataset dataset :
                datasets) {
            System.out.println(dataset.getFile());
            TSTask baseTask = new TSTask(dataset, new TSSolverBase(4, 3, 4));
            TSTask myTask = new TSTask(dataset, new TSSolverMy(3, 3, 4));
            double baseCriterion = (baseTask.getLength() - dataset.getOptimum())/dataset.getOptimum();
            double myCriterion = (myTask.getLength() - dataset.getOptimum())/dataset.getOptimum();
            average += baseCriterion - myCriterion;
            System.out.println("    opt: " + dataset.getOptimum() + ", base: " + baseTask.getLength() + ", my: " + myTask.getLength() +
                    ", criterion base: " + baseCriterion +
                    ", criterion my: " + myCriterion);
        }
        average /= datasets.size();
        System.out.println("Average efficient of my alg: " + average);
    }
}
