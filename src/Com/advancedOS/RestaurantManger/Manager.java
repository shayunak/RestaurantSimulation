package Com.advancedOS.RestaurantManger;

import Com.advancedOS.RestaurantManger.Resources.Table;

import java.util.ArrayList;

public class Manager {
    private ArrayList<CookThread> cooks;
    private ArrayList<DinerThread> diner;
    private static Integer globalTime = 0;
    private final Integer STEP_TIME = 10;
    private Integer numberOfDiners;
    private Integer numberOfCooks;
    private Integer numberOfTables;
    private ArrayList<order> orders;

    public Manager(InputScanner in) {
        numberOfDiners = in.getNumberOfDiners();
        numberOfCooks = in.getNumberOfCooks();
        numberOfTables = in.getNumberOfTables();
        orders = in.getOrders();
    }

    public void startSimulation() {
        System.out.format("In Restaurant 6431, there are %d tables, %d cooks, " +
                "and %d dinner will be coming.", numberOfTables, numberOfCooks, numberOfDiners);
        initializeResources();

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
