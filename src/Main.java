import cuttingstock.CuttingStockStarter;
import deliveryoptimization.DeliveryOptimizationStarter;
import general.Starter;
import orderselection.OrderSelectionStarter;

public class Main {
    public static void main(String[] args) {
        Starter starter = new DeliveryOptimizationStarter();
        starter.start();
    }
}
