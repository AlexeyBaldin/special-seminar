package travellingsalesman.model;

import java.util.ArrayList;

public class TSResult {

    private ArrayList<Integer> order;
    private int length;

    public TSResult(ArrayList<Integer> order, int length) {
        this.order = order;
        this.length = length;
    }

    public ArrayList<Integer> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Integer> order) {
        this.order = order;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
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
