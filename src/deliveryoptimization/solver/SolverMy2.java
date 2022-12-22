package deliveryoptimization.solver;

import deliveryoptimization.model.DODataset;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SolverMy2 extends SolverMy {
    public SolverMy2(DODataset dataset) {
        super(dataset);
    }

//    @Override
//    protected int lower(ArrayList<Integer> v) {
//        if(lowerCache.containsKey(v)) {
//            return lowerCache.get(v);
//        }
//
//
//        ArrayList<Integer> beta = getBeta(v);
//
//        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
//        int path = pathAndZ.getKey();
//        ArrayList<Integer> z = pathAndZ.getValue();
//
//        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
//        ArrayList<Integer> pathForPairs = new ArrayList<>();
//
//        for (int i = 0; i < beta.size(); i++) {
//            if(i == beta.size() - 1) {
//                pairs.add(new Pair<>(beta.get(i), beta.get(0)));
//            } else {
//                pairs.add(new Pair<>(beta.get(i), beta.get(i+1)));
//            }
//        }
//
//        for (Pair<Integer, Integer> p :
//                pairs) {
//            z.set(p.getKey(), path + tIJ.get(v.get(v.size() - 1)).get(p.getKey()) + tIJ.get(p.getKey()).get(p.getValue()));
//        }
//
//        int lower = bound(z);
//        lowerCache.put(v, lower);
//        return lower;
//    }



//    protected int upper(ArrayList<Integer> v) {
//        if(upperCache.containsKey(v)) {
//            return upperCache.get(v);
//        }
//
//        ArrayList<Integer> beta = getBeta(v);
//
//        Pair<Integer, ArrayList<Integer>> pathAndZ = getPathAndZ(v);
//        int path = pathAndZ.getKey();
//        ArrayList<Integer> z = pathAndZ.getValue();
//
//        int k = v.size();
//        int last = v.get(v.size() - 1);
//
//        while(k != n) {
//
//
//            int min = Integer.MAX_VALUE;
//            Integer minIndex = -1;
//            int pathB = 0;
//            for (Integer b :
//                    beta) {
//                int tempPath = path + tIJ.get(last).get(b);
//                int tempMin = tD.get(b-1) - tempPath;
//                if(tempMin < 0) {
//                    continue;
//                }
//                if(tempMin < min) {
//                    min = tempMin;
//                    minIndex = b;
//                    pathB = tempPath;
//                }
//            }
//
//            if(minIndex != -1) {
//                z.set(minIndex, pathB);
//                beta.remove(minIndex);
//                path = pathB;
//                last = minIndex;
//            }
//            k++;
//
//        }
//
//        int upper = bound(z) + beta.size();
//        upperCache.put(v, upper);
//        return upper;
//    }

    protected void clipping(ArrayList<ArrayList<Integer>> V) {
        Set<ArrayList<Integer>> delete = new HashSet<>();
        int deleted = 0;
        for (int i = 0; i < V.size(); i++) {
            for (int j = i+1; j < V.size(); j++) {
                ArrayList<Integer> v = V.get(i);
                ArrayList<Integer> v_ = V.get(j);

                //System.out.println(v + "  " + v_ + "  " + upper(v) + "  " + lower(v_));

                if(v != v_) {
                    if(upper(v) <= lower(v_)) {
                        delete.add(v_);
                        continue;
                    }

                    if(upper(v_) <= lower(v)) {
                        delete.add(v);
                    }

                    if(upper(v) == upper(v_) && lower(v) == lower(v_) && deleted < 9) {
                        deleted++;
                        if(v.size() < v_.size()) {
                            delete.add(v_);
                        } else {
                            delete.add(v);
                        }
                        //delete.add(v_);
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
}
