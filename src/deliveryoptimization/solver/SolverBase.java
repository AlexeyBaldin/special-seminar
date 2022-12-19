package deliveryoptimization.solver;

import deliveryoptimization.model.DODataset;
import deliveryoptimization.model.DOResult;
import javafx.util.Pair;

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


    protected ArrayList<Integer> getBeta(ArrayList<Integer> v) {
        ArrayList<Integer> beta = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            beta.add(i);
        }
        for (Integer num :
                v) {
            beta.remove(num);
        }
        return beta;
    }

    protected Pair<Integer, ArrayList<Integer>> getPathAndZ(ArrayList<Integer> v) {
        ArrayList<Integer> z = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            z.add(0);
        }

        int path = tIJ.get(0).get(v.get(0));
        z.set(v.get(0), path);
        for(int i = 1; i < v.size(); i++) {
            path += tIJ.get(v.get(i-1)).get(v.get(i));
            z.set(v.get(i), path);
        }

        return new Pair<>(path, z);
    }

    protected int estimate(ArrayList<Integer> z) {
        int estimate = 0;
        for(int i = 0; i < n; i++) {
            if(z.get(i+1) > tD.get(i)) {
                estimate++;
            }
        }
        return estimate;
    }
    protected int lower(ArrayList<Integer> v) {
        ArrayList<Integer> beta = getBeta(v);

        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
        int path = pathAndZ.getKey();
        ArrayList<Integer> z = pathAndZ.getValue();

        for (Integer b : beta) {
            z.set(b, path + tIJ.get(v.get(v.size() - 1)).get(b));
        }

//        System.out.println(v);
//        System.out.println(beta);
//        System.out.println(z);

        return estimate(z);
    }

    protected int upper(ArrayList<Integer> v) {
        ArrayList<Integer> beta = getBeta(v);

        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
        int path = pathAndZ.getKey();
        ArrayList<Integer> z = pathAndZ.getValue();

        int k = v.size();

        while(k != n) {


            int min = Integer.MAX_VALUE;
            Integer minIndex = -1;
            int pathB = 0;
            for (Integer b :
                    beta) {
                int tempPath = path + tIJ.get(v.get(v.size() - 1)).get(b);
                int tempMin = tD.get(b-1) - tempPath;
                if(tempMin < 0) {
                    continue;
                }
                if(tempMin < min) {
                    min = tempMin;
                    minIndex = b;
                    pathB = tempPath;
                }
            }

            if(minIndex != -1) {
                z.set(minIndex, pathB);
                beta.remove(minIndex);
                path = pathB;
            }
            k++;
        }

//        System.out.println(beta);
//        System.out.println(z);

        return estimate(z) + beta.size();
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


        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);

        System.out.println(tIJ);

        upper(test);



        int failed = 0;


        return new DOResult(this.leaf, failed, order);
    }
}
