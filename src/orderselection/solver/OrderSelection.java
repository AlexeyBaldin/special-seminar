package orderselection.solver;

import orderselection.model.CostIncome;
import orderselection.model.Result;

import java.util.ArrayList;

public interface OrderSelection {
    Result solve(int performance, int count, ArrayList<CostIncome> costIncomes);
}
