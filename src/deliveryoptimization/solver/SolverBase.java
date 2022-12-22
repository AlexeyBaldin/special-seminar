package deliveryoptimization.solver;

import deliveryoptimization.model.DODataset;
import deliveryoptimization.model.DOResult;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SolverBase implements DOSolver{


    protected int leaf = 0;

    protected final int n;
    protected final ArrayList<Integer> tD;
    protected final ArrayList<ArrayList<Integer>> tIJ;

    public SolverBase(DODataset dataset) {
        this.n = dataset.getOrders();
        this.tD = dataset.getTerms();
        this.tIJ = dataset.getTimesMatrix();
    }

    protected HashMap<ArrayList<Integer>, Integer> lowerCache = new HashMap<>();
    protected HashMap<ArrayList<Integer>, Integer> upperCache = new HashMap<>();

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
            ArrayList<ArrayList<Integer>> result = bfs(V);

            return result.get(0);
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

    protected int bound(ArrayList<Integer> z) {
        int bound = 0;
        for(int i = 0; i < n; i++) {
            if(z.get(i+1) > tD.get(i)) {
                bound++;
            }
        }
        return bound;
    }
    protected int lower(ArrayList<Integer> v) {
        if(lowerCache.containsKey(v)) {
            return lowerCache.get(v);
        }


        ArrayList<Integer> beta = getBeta(v);

        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
        int path = pathAndZ.getKey();
        ArrayList<Integer> z = pathAndZ.getValue();

        for (Integer b : beta) {
            z.set(b, path + tIJ.get(v.get(v.size() - 1)).get(b));
        }

        int lower = bound(z);
        lowerCache.put(v, lower);
        return lower;
    }

    protected int upper(ArrayList<Integer> v) {
        if(upperCache.containsKey(v)) {
            return upperCache.get(v);
        }

        ArrayList<Integer> beta = getBeta(v);

        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
        int path = pathAndZ.getKey();
        ArrayList<Integer> z = pathAndZ.getValue();

        int k = v.size();
        int last = v.get(v.size() - 1);

        while(k != n) {


            int min = Integer.MAX_VALUE;
            Integer minIndex = -1;
            int pathB = 0;
            for (Integer b :
                    beta) {
                int tempPath = path + tIJ.get(last).get(b);
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
                last = minIndex;
            }
            k++;

        }

        int upper = bound(z) + beta.size();
        upperCache.put(v, upper);
        return upper;
    }


    protected void clipping(ArrayList<ArrayList<Integer>> V) {
        Set<ArrayList<Integer>> delete = new HashSet<>();
        for (int i = 0; i < V.size(); i++) {
            for (int j = i+1; j < V.size(); j++) {
                ArrayList<Integer> v = V.get(i);
                ArrayList<Integer> v_ = V.get(j);



                if(v != v_) {
                    if(upper(v) <= lower(v_)) {
                        delete.add(v_);
                        continue;
                    }

                    if(upper(v_) <= lower(v)) {
                        delete.add(v);
                    }
                }
            }
        }

        for (ArrayList<Integer> v :
                delete) {
            V.remove(v);
            this.leaf++;
        }

    }

    protected ArrayList<Integer> branchAndBound() {

        ArrayList<ArrayList<Integer>> V = new ArrayList<>();

        while(!(V.size() == 1 && V.get(0).size() == this.n && upper(V.get(0)) == lower(V.get(0)))) {

            //System.out.println("      start while");

            ArrayList<Integer> v = null;
            if(V.size() == 0) {
                v = new ArrayList<>();
            } else if(V.size() == 1) {
                v = V.get(0);
            } else {
                //System.out.println("      start branching");
                v = branching(V);
                //System.out.println("      end branching");
            }



            ArrayList<Integer> beta = getBeta(v);
            V.remove(v);
            this.leaf++;
            for (Integer b :
                    beta) {
                ArrayList<Integer> temp = new ArrayList<>(v);
                temp.add(b);
                V.add(temp);
            }
            //System.out.println(this.leaf + "   " + V.size() + "   " + V.get(0).size());
            clipping(V);
            //System.out.println("    " + V.size());
        }

        return V.get(0);
    }

    @Override
    public DOResult solve() {
        ArrayList<Integer> order = branchAndBound();


        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(order);
        ArrayList<Integer> z = pathAndZ.getValue();
        int fail = 0;
        for (int i = 0; i < n; i++) {
            if(z.get(i+1) > this.tD.get(i)) {
                fail++;
            }
        }
        order.add(0, 0);


        return new DOResult(this.leaf-1, fail, order);
    }
}
