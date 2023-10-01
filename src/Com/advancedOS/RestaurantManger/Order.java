package Com.advancedOS.RestaurantManger;

public class Order {
    public final Object isReady = new Object();
    public Integer dinerId;
    public Integer numberOfBurgersRemaining;
    public Integer numberOfFriesRemaining;
    public Boolean isCokePrepared;

    public Order(Integer dinerId, Integer numberOfBurgersRemaining, Integer numberOfFriesRemaining, Boolean isCokePrepared) {
        this.dinerId = dinerId;
        this.numberOfBurgersRemaining = numberOfBurgersRemaining;
        this.numberOfFriesRemaining = numberOfFriesRemaining;
        this.isCokePrepared = isCokePrepared;
    }
}
