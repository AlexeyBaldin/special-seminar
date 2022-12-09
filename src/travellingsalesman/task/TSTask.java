package travellingsalesman.task;

import travellingsalesman.model.TSDataset;
import travellingsalesman.model.TSResult;
import travellingsalesman.solver.TSSolver;

public class TSTask {

    private TSResult result;

    public TSTask(TSDataset dataset, TSSolver solver) {

        this.result = solver.solve(dataset);
    }

    public int getLength() {
        return this.result.getLength();
    }

}
