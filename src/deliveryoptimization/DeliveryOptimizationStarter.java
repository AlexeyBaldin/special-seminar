package deliveryoptimization;

import deliveryoptimization.model.DODataset;
import deliveryoptimization.model.DOResult;
import deliveryoptimization.solver.SolverBase;
import deliveryoptimization.solver.SolverMy;
import deliveryoptimization.solver.SolverMy2;
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
        for (int i = 8; i < 9; i++) {
            DODataset dataset = datasets.get(i);

            System.out.println(dataset.getFile());



            SolverBase solverBase = new SolverBase(dataset);
            DOResult resultBase = solverBase.solve();
            System.out.println("    optimum=" + resultBase.getFailed() + ", baseLeaf=" + resultBase.getLeaf());


            SolverBase solverMy = new SolverMy(dataset);
            DOResult resultMy = solverMy.solve();
            System.out.println("    optimum=" + resultMy.getFailed() + ", myLeaf=" + resultMy.getLeaf());

            SolverBase solverMy2 = new SolverMy2(dataset);
            DOResult resultMy2 = solverMy2.solve();
            System.out.println("    optimum=" + resultMy2.getFailed() + ", myLeaf2=" + resultMy2.getLeaf());



            //System.out.println(resultBase.getOrder());
        }

    }
}
