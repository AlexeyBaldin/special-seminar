package orderselection.permutation;

import orderselection.model.CostIncome;

import java.util.ArrayList;

public class NoChangePermutator implements Permutator {
    @Override
    public ArrayList<CostIncome> getNewOrders(int performance, int count, ArrayList<CostIncome> costIncomes) {
        return costIncomes;
    }
}
