package orderselection.model;

import java.util.ArrayList;

public class Result {
    private final int maxIncome;
    private final ArrayList<Integer> orders;

    public Result(int maxIncome, ArrayList<Integer> orders) {
        this.maxIncome = maxIncome;
        this.orders = orders;
    }

    public int getMaxIncome() {
        return maxIncome;
    }

    public ArrayList<Integer> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Result{" +
                "maxIncome=" + maxIncome +
                ", orders=" + orders +
                '}';
    }
}
