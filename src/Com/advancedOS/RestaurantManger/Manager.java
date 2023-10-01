package Com.advancedOS.RestaurantManger;

import Com.advancedOS.RestaurantManger.Resources.Table;

import java.util.ArrayList;

public class Manager {
    private ArrayList<CookThread> cooks;
    private ArrayList<DinerThread> diners;
    private static Integer globalTime = 0;
    private final Integer STEP_TIME = 10;
    private Integer numberOfDiners;
    private Integer numberOfCooks;
    private Integer numberOfTables;
    private ArrayList<orderInput> orders;

    public Manager(InputScanner in) {
        numberOfDiners = in.getNumberOfDiners();
        numberOfCooks = in.getNumberOfCooks();
        numberOfTables = in.getNumberOfTables();
        orders = in.getOrders();
    }

    private void initializeDiners() {
        diners = new ArrayList<>();
        for (int i = 0; i < numberOfDiners; i++){
            Order dinerOrder = new Order(orders.get(i).numberOfBurgers(), orders.get(i).numberOfFries(), !orders.get(i).isCokeOrdered());
            diners.add(new DinerThread(i, dinerOrder, orders.get(i).timeArrived()));
        }
    }

    private void initializeCooks() {
        cooks = new ArrayList<>();
        for (int i = 0; i < numberOfCooks; i++)
            cooks.add(new CookThread(i));
    }

    private void runThreads() {
        diners.forEach(Thread::start);
        cooks.forEach(Thread::start);
    }

    public void startSimulation() {
        System.out.format("In Restaurant 6431, there are %d tables, %d cooks, " +
                "and %d dinner will be coming.", numberOfTables, numberOfCooks, numberOfDiners);
        initializeResources();
        initializeDiners();
        initializeCooks();
        runThreads();
    }

    private void initializeResources() {
        initializeTables();
    }

    private void initializeTables() {
        Table.availableTables = new ArrayList<>();
        for (int i = 0; i < numberOfTables; i++)
            Table.availableTables.add(new Table(i));
    }
}
