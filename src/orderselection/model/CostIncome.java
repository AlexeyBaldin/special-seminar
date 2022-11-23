package orderselection.model;

public class CostIncome {

    private final int order;

    private final int cost;
    private final int income;

    public CostIncome(int order, int cost, int income) {
        this.order = order;
        this.cost = cost;
        this.income = income;
    }

    public int getOrder() {
        return order;
    }
    public int getCost() {
        return cost;
    }

    public int getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "{" +
                "order=" + order +
                ", cost=" + cost +
                ", income=" + income +
                '}';
    }
}
