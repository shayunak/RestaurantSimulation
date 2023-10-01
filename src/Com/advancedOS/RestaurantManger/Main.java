package Com.advancedOS.RestaurantManger;

public class Main {
    public static void main(String[] args) {
        InputScanner in = new InputScanner();
        in.getInput();
        Manager manager = new Manager(in);
        manager.startSimulation();
    }
}
