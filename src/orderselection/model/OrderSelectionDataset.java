package orderselection.model;

import general.Dataset;

import java.util.ArrayList;

public class OrderSelectionDataset implements Dataset {

    private final int performance;
    private final int count;
    private final ArrayList<CostIncome> costIncomes;

    private final String file;

    public OrderSelectionDataset(int performance, int count, ArrayList<Integer> cost, ArrayList<Integer> income, String file) {
        this.performance = performance;
        this.count = count;

        this.costIncomes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            costIncomes.add(new CostIncome(i+1, cost.get(i), income.get(i)));
        }

        this.file = file;
    }

    public int getPerformance() {
        return performance;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<CostIncome> getCostIncomes() {
        return new ArrayList<>(costIncomes);
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "performance=" + performance +
                ", count=" + count +
                ", costIncomes=" + costIncomes +
                ", file='" + file + '\'' +
                '}';
    }
}
