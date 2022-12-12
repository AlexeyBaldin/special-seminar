package travellingsalesman;

import general.Dataset;
import general.Starter;
import travellingsalesman.model.TSDataset;
import travellingsalesman.solver.TSSolverBase;
import travellingsalesman.solver.TSSolverMy;
import travellingsalesman.solver.TSSolverMy2;
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

        int startClusters = 4;
        int endClusters = 6;
        int startStopper = 4;
        int endStopper = 8;

        double average = 0;
        for (TSDataset dataset :
                datasets) {
            System.out.println(dataset.getFile());
            TSTask baseTask = new TSTask(dataset, new TSSolverBase(4, 3, 4));
            double baseCriterion = (baseTask.getLength() - dataset.getOptimum())/dataset.getOptimum();



            TSTask myTask = null;
            int chooseClusters = 0;
            int chooseStopper = 0;
            double myCriterion = Double.MAX_VALUE;
            for(int i = startClusters; i <= endClusters; i++) {
                for (int j = startStopper; j <= endStopper; j++) {
                    System.out.println(i + " " + j);
                    myTask = new TSTask(dataset, new TSSolverMy(i, 3, j));
                    double criterion = (myTask.getLength() - dataset.getOptimum())/dataset.getOptimum();

                    if(myCriterion > criterion)  {
                        myCriterion = criterion;
                        chooseClusters = i;
                        chooseStopper = j;
                    }
                }
            }

            average += baseCriterion - myCriterion;
            System.out.println("    opt: " + dataset.getOptimum() + ", base: " + baseTask.getLength() + ", my: " + myTask.getLength() +
                    ", criterion base: " + baseCriterion +
                    ", criterion my: " + myCriterion + " with clusters=" + chooseClusters + " and stopper=" + chooseStopper);
        }
        average /= datasets.size();
        System.out.println("Average efficient of my 1 alg: " + average);
    }
}
