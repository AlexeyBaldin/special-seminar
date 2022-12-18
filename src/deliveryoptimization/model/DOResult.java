package deliveryoptimization.model;

import java.util.ArrayList;

public class DOResult {

    private int leaf;
    private int failed;
    private ArrayList<Integer> order;

    public DOResult(int leaf, int failed, ArrayList<Integer> order) {
        this.leaf = leaf;
        this.failed = failed;
        this.order = order;
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public ArrayList<Integer> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Integer> order) {
        this.order = order;
    }
}
