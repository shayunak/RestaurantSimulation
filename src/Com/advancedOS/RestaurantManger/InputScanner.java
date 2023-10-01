package Com.advancedOS.RestaurantManger;

import java.util.ArrayList;
import java.util.Scanner;

record orderInput(Integer timeArrived, Integer numberOfBurgers, Integer numberOfFries, Boolean isCokeOrdered) {}

public class InputScanner {
    private Integer numberOfDiners;
    private Integer numberOfTables;
    private Integer numberOfCooks;
    private ArrayList<orderInput> orders;
    private Scanner scanner;

    public InputScanner(){
        this.numberOfDiners = 0;
        this.numberOfTables = 0;
        this.numberOfCooks = 0;
        this.orders = new ArrayList<orderInput>();
        scanner = new Scanner(System.in);
    }

    public void getInput(){
        this.numberOfDiners = Integer.parseInt(scanner.nextLine());
        this.numberOfTables = Integer.parseInt(scanner.nextLine());
        this.numberOfCooks = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < this.numberOfDiners; i++) {
            String lineOrder = scanner.nextLine();
            String[] splitOrder = lineOrder.split(",");
            orders.add(new orderInput(Integer.parseInt(splitOrder[0]), Integer.parseInt(splitOrder[1]), Integer.parseInt(splitOrder[2]),
                    Integer.parseInt(splitOrder[3].replaceAll("\\s", "")) == 1));
        }
    }

    public Integer getNumberOfDiners() {
        return numberOfDiners;
    }

    public ArrayList<orderInput> getOrders() {
        return orders;
    }

    public Integer getNumberOfCooks() {
        return numberOfCooks;
    }

    public Integer getNumberOfTables() {
        return numberOfTables;
    }
}
