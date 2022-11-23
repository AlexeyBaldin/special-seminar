package orderselection.permutation;

import orderselection.model.CostIncome;

import java.util.ArrayList;

public class ProfitSortPermutator implements Permutator{
    @Override
    public ArrayList<CostIncome> getNewOrders(int performance, int count, ArrayList<CostIncome> costIncomes) {

        costIncomes.sort((o1, o2) -> Double.compare((double) o2.getIncome() / o2.getCost(), (double) o1.getIncome() / o1.getCost()));

        return costIncomes;
    }
}
