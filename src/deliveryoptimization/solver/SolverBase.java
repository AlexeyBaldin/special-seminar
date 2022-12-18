package deliveryoptimization.solver;

import deliveryoptimization.model.DODataset;
import deliveryoptimization.model.DOResult;

import java.util.ArrayList;

public class SolverBase implements DOSolver{


    private int leaf = 0;

    private final int n;
    private final ArrayList<Integer> tD;
    private final ArrayList<ArrayList<Integer>> tIJ;

    public SolverBase(DODataset dataset) {
        this.n = dataset.getOrders();
        this.tD = dataset.getTerms();
        this.tIJ = dataset.getTimesMatrix();
    }

    protected ArrayList<ArrayList<Integer>> bfs(ArrayList<ArrayList<Integer>> V) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        for (ArrayList<Integer> v_ :
                V) {
            if(min > v_.size()) {
                min = v_.size();
            }
        }

        for (ArrayList<Integer> v_ :
                V) {
            if(min == v_.size()) {
                result.add(v_);
            }
        }

        return result;
    }

    protected ArrayList<Integer> branching(ArrayList<ArrayList<Integer>> V) {
        if(V.size() == 1) {
            return V.get(0);
        } else {
            return bfs(V).get(0);

            //Добавить другие методы ветвления
        }
    }

    protected int lower(ArrayList<Integer> v) {
        ArrayList<Integer> beta = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            beta.add(i);
        }
        for (Integer num :
                v) {
            beta.remove(num);
        }




        return 0;
    }

    protected ArrayList<Integer> branchAndBound() {

        ArrayList<ArrayList<Integer>> V = new ArrayList<>();

        while(true) {


            break;
        }

        return null;
    }

    @Override
    public DOResult solve() {
        ArrayList<Integer> order = branchAndBound();






        int failed = 0;


        return new DOResult(this.leaf, failed, order);
    }
}
