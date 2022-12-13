package orderselection;

import com.sun.org.apache.xpath.internal.operations.Or;
import general.Dataset;
import general.Starter;
import orderselection.model.OrderSelectionDataset;
import orderselection.permutation.AveragePriceWithCoeffPermutator;
import orderselection.permutation.ProfitSortPermutator;
import orderselection.solver.OrderSelectionColumn;
import orderselection.solver.OrderSelectionRecurrent;

import java.util.ArrayList;

public class OrderSelectionStarter implements Starter {
    private final ArrayList<OrderSelectionDataset> datasets = new ArrayList<>();

    public OrderSelectionStarter() {
        ArrayList<Dataset> orderSelectionDatasets = new OrderSelectionReader().getDatasets("orderselection");
        orderSelectionDatasets.forEach(o -> datasets.add((OrderSelectionDataset)o));
    }

    @Override
    public void start() {
        time();
        efficient();
    }

    public void time() {
        datasets.forEach(dataset -> {
            Task table = new Task(dataset, new OrderSelectionColumn());
            Task recurrent = new Task(dataset, new OrderSelectionRecurrent());

            System.out.println(dataset.getFile());
            System.out.println("    tableTime=" + table.getTime() + ", recurrentTime=" + recurrent.getTime());
        });
    }

    public void efficient() {
        datasets.forEach(dataset -> {
            Task fullTask = new Task(dataset, new OrderSelectionColumn());
            Task baseTask = new Task(dataset, new OrderSelectionColumn(), new ProfitSortPermutator(), 3);

            Task myTask = new Task(dataset, new OrderSelectionColumn(), new AveragePriceWithCoeffPermutator(), 3,
                    -0.1, 0.1, 0.001);

            double baseQuality = (double) baseTask.getMaxIncome() / fullTask.getMaxIncome();
            double myQuality = (double) myTask.getMaxIncome() / fullTask.getMaxIncome();
            double myProfit = myQuality - baseQuality;

            System.out.println(dataset.getFile());
            System.out.println("    FullMaxIncome=" + fullTask.getMaxIncome() + ", BasePermutation: maxIncome=" + baseTask.getMaxIncome() +
                    " | MyPermutation: maxIncome=" + myTask.getMaxIncome() + ", coefficient=" + myTask.getCoefficient() + " | baseQuality=" +
                    baseQuality + " | myQuality=" + myQuality + " | myProfit=" + myProfit);
        });
    }
}
