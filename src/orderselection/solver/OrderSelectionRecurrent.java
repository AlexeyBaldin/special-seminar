package orderselection.solver;

import orderselection.model.CostIncome;
import orderselection.model.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderSelectionRecurrent implements OrderSelection {


    HashMap<Integer, HashMap<Integer, Integer>> cache = new HashMap<>();

    private int recursive(int k, int w, ArrayList<CostIncome> costIncomes) {

        if (k == 0) {
            if (costIncomes.get(0).getCost() <= w) {
                return costIncomes.get(0).getIncome();
            } else {
                return 0;
            }
        } else {
            if (costIncomes.get(k).getCost() <= w) {
                int no = recursive(k - 1, w, costIncomes);
                int yes = recursive(k - 1, w - costIncomes.get(k).getCost(), costIncomes) + costIncomes.get(k).getIncome();

                return Math.max(yes, no);
            } else {
                return recursive(k - 1, w, costIncomes);
            }
        }
    }

    private int recursiveCache(int k, int w, ArrayList<CostIncome> costIncomes) {

        if (cache.containsKey(k)) {
            if (cache.get(k).containsKey(w)) {
                return cache.get(k).get(w);
            }
        }

        if (k == 0) {
            if (costIncomes.get(0).getCost() <= w) {
                return costIncomes.get(0).getIncome();
            } else {
                return 0;
            }
        } else {

            int previous = recursiveCache(k - 1, w, costIncomes);
            if (!cache.containsKey(k - 1)) {
                HashMap<Integer, Integer> wHashMap = new HashMap<>();
                wHashMap.put(w, previous);
                cache.put(k - 1, wHashMap);
            } else {
                if (!cache.get(k - 1).containsKey(w)) {
                    cache.get(k - 1).put(w, previous);
                }
            }
//            System.out.println(k + "  " + w + "  " + "  " + previous);


            if (costIncomes.get(k).getCost() <= w) {
                int current = recursiveCache(k - 1, w - costIncomes.get(k).getCost(), costIncomes) + costIncomes.get(k).getIncome();

                if (!cache.containsKey(k)) {
                    cache.put(k, new HashMap<>());
                    if (!cache.get(k).containsKey(w)) {
                        cache.get(k).put(w, Math.max(current, previous));
                    }
                }

                return Math.max(current, previous);
            } else {
                return previous;
            }
        }
    }

    @Override
    public Result solve(int performance, int count, ArrayList<CostIncome> costIncomes) {

        int maxIncome = recursiveCache(count - 1, performance, costIncomes);

        return new Result(maxIncome, new ArrayList<>());
    }

}
