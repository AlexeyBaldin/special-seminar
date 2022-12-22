package deliveryoptimization.solver;

import deliveryoptimization.model.DODataset;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SolverMy extends SolverBase{
    public SolverMy(DODataset dataset) {
        super(dataset);
    }


    protected ArrayList<ArrayList<Integer>> lowerFilter(ArrayList<ArrayList<Integer>> V) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        ArrayList<Integer> lowers = new ArrayList<>();
        for (int i = 0; i < V.size(); i++) {
            lowers.add(lower(V.get(i)));
        }

        int min = Integer.MAX_VALUE;
        for (Integer i :
                lowers) {
            if(min > i) {
                min = i;
            }
        }

        for (int i = 0; i < V.size(); i++) {
            if(lowers.get(i) == min) {
                result.add(V.get(i));
            }
        }

        return result;
    }

    protected ArrayList<ArrayList<Integer>> upperFilter(ArrayList<ArrayList<Integer>> V) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        ArrayList<Integer> uppers = new ArrayList<>();
        for (int i = 0; i < V.size(); i++) {
            uppers.add(upper(V.get(i)));
        }

        int min = Integer.MAX_VALUE;
        for (Integer i :
                uppers) {
            if(min > i) {
                min = i;
            }
        }

        for (int i = 0; i < V.size(); i++) {
            if(uppers.get(i) == min) {
                result.add(V.get(i));
            }
        }

        return result;
    }



    @Override
    protected ArrayList<Integer> branching(ArrayList<ArrayList<Integer>> V) {
        if(V.size() == 1) {
            return V.get(0);
        } else {
//            ArrayList<ArrayList<Integer>> result = bfs(V);
//            result = lowerFilter(result);
//            result = upperFilter(result);
            ArrayList<ArrayList<Integer>> result = lowerFilter(V);
            result = upperFilter(result);
            result = bfs(result);
            //System.out.println(result.size()/2);

            return result.get(result.size()/2);
        }
    }


}
