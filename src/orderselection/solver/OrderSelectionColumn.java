package orderselection.solver;

import orderselection.model.CostIncome;
import orderselection.model.Result;

import java.util.ArrayList;

public class OrderSelectionColumn implements OrderSelection {

    private static class CurrentIncomeAndPath {
        int currentIncome;
        ArrayList<Integer> path;

        public CurrentIncomeAndPath(int currentIncome, ArrayList<Integer> path) {
            this.currentIncome = currentIncome;
            this.path = path;
        }

        @Override
        public String toString() {
            return "CurrentIncomeAndPath{" +
                    "currentIncome=" + currentIncome +
                    ", path=" + path +
                    '}';
        }
    }

    public ArrayList<CurrentIncomeAndPath> fillColumn(int performance, int order, int cost, int income, ArrayList<CurrentIncomeAndPath> previousColumn) {
        ArrayList<CurrentIncomeAndPath> newColumn = new ArrayList<>(performance);

        newColumn.add(0, previousColumn.get(0));

        for(int i = 2; i <= performance; i++) {
            if(i == cost) {
                if(income > previousColumn.get(i - 1).currentIncome) {
                    ArrayList<Integer> path = new ArrayList();
                    path.add(order);
                    newColumn.add(new CurrentIncomeAndPath(income, path));
                } else {
                    newColumn.add(previousColumn.get(i - 1));
                }
            } else if(i > cost) {
                if(income + previousColumn.get(i - cost - 1).currentIncome > previousColumn.get(i - 1).currentIncome) {
                    ArrayList<Integer> path = new ArrayList(previousColumn.get(i - cost - 1).path);
                    path.add(order);
                    newColumn.add(new CurrentIncomeAndPath(income + previousColumn.get(i - cost - 1).currentIncome, path));
                } else {
                    newColumn.add(previousColumn.get(i - 1));
                }
            } else {
                newColumn.add(previousColumn.get(i - 1));
            }
        }

        return newColumn;
    }

    @Override
    public Result solve(int performance, int count, ArrayList<CostIncome> costIncomes) {

        ArrayList<CurrentIncomeAndPath> firstColumn = new ArrayList<>(performance);
        ArrayList<CurrentIncomeAndPath> secondColumn;

//        System.out.println(performance);
//        System.out.println(count);
//        System.out.println(cost);
//        System.out.println(income);


        for(int i = 1; i <= performance; i++) {
            if(i >= costIncomes.get(0).getCost()) {
                ArrayList<Integer> path = new ArrayList<>();
                path.add(costIncomes.get(0).getOrder());
                firstColumn.add(new CurrentIncomeAndPath(costIncomes.get(0).getIncome(), path));
            } else {
                firstColumn.add(new CurrentIncomeAndPath(0, new ArrayList<>()));
            }
        }

        for(int order = 2; order <= count; order++) {
            secondColumn = fillColumn(performance, costIncomes.get(order - 1).getOrder(), costIncomes.get(order - 1).getCost(), costIncomes.get(order - 1).getIncome(), firstColumn);
            firstColumn = secondColumn;
        }

        return new Result(firstColumn.get(performance - 1).currentIncome, firstColumn.get(performance - 1).path);
    }
}
