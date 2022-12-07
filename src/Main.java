import cuttingstock.CuttingStockStarter;
import deliveryoptimization.DeliveryOptimizationStarter;
import general.Starter;
import orderselection.OrderSelectionStarter;
import travellingsalesman.TravellingSalesmanStarter;

public class Main {
    public static void main(String[] args) {
        Starter starter = new TravellingSalesmanStarter();
        starter.start();
    }
}
