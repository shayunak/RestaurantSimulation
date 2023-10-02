package Com.advancedOS.RestaurantManger;

public class OrderInput {
    public Integer timeArrived;
    public Integer numberOfBurgers;
    public Integer numberOfFries;
    public Boolean isCokeOrdered;

    public OrderInput(Integer timeArrived, Integer numberOfBurgers, Integer numberOfFries, Boolean isCokeOrdered) {
        this.timeArrived = timeArrived;
        this.numberOfBurgers = numberOfBurgers;
        this.numberOfFries = numberOfFries;
        this.isCokeOrdered = isCokeOrdered;
    }
}
