package orderselection.permutation;

import orderselection.model.CostIncome;

import java.util.ArrayList;

public class AveragePriceWithCoeffPermutator implements PermutatorWithCoefficient {

    private double coefficient = 0.0;

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public ArrayList<CostIncome> getNewOrders(int performance, int count, ArrayList<CostIncome> costIncomes) {

        double average = 0.0;
        for (CostIncome costIncome :
                costIncomes) {
            average += (double) costIncome.getIncome() / costIncome.getCost();
        }
        average /= costIncomes.size();

        ArrayList<CostIncome> left = new ArrayList<>();
        ArrayList<CostIncome> right = new ArrayList<>();

        for (CostIncome costIncome :
                costIncomes) {
            if((double) costIncome.getIncome() / costIncome.getCost() > average + coefficient) {
                left.add(costIncome);
            } else {
                right.add(costIncome);
            }
        }

        left.sort((o1, o2) -> Double.compare((double) o1.getIncome() / o1.getCost(), (double) o2.getIncome() / o2.getCost()));
        right.sort((o1, o2) -> Double.compare((double) o2.getIncome() / o2.getCost(), (double) o1.getIncome() / o1.getCost()));
        //System.out.println(coefficient + "  " + costIncomes.size() + "  " + left.size() + "  " + right.size());
        ArrayList<CostIncome> newOrder = new ArrayList<>(left);
        newOrder.addAll(right);

        return newOrder;
    }
}
