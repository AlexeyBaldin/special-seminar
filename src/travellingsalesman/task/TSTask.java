package travellingsalesman.task;

import travellingsalesman.model.TSDataset;
import travellingsalesman.model.TSResult;
import travellingsalesman.solver.TSSolver;

public class TSTask {

    private final TSResult result;

    public TSTask(TSDataset dataset, TSSolver solver) {

        this.result = solver.solve(dataset);
    }

    public double getLength() {
        return this.result.getLength();
    }

}
