package travellingsalesman.model;

import java.util.ArrayList;

public class TSResult {

    private ArrayList<Long> order;
    private double length;

    public TSResult(ArrayList<Long> order, long length) {
        this.order = order;
        this.length = length;
    }

    public ArrayList<Long> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Long> order) {
        this.order = order;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "TSResult{" +
                "order=" + order +
                ", length=" + length +
                '}';
    }
}
