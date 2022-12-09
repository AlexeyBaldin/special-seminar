package travellingsalesman.solver;

import travellingsalesman.model.TSDataset;
import travellingsalesman.model.TSResult;

public interface TSSolver {
    TSResult solve(TSDataset dataset);
}
