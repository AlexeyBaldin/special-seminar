import deliveryoptimization.DeliveryOptimizationStarter;
import general.Starter;
import orderselection.OrderSelectionStarter;
import travellingsalesman.TSStarter;

public class Main {
    public static void main(String[] args) {
        Starter starter = new DeliveryOptimizationStarter();
        starter.start();
    }
}
