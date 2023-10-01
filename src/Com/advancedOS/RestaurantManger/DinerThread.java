package Com.advancedOS.RestaurantManger;

import Com.advancedOS.RestaurantManger.Resources.Table;

record Order(Integer numberOfBurgersRemaining, Integer numberOfFriesRemaining, Boolean isCokePrepared) {}

public class DinerThread extends Thread {
    private Integer myId;
    private Order myOrder;
    private Table myTable;
    private Integer timeArrived;
    private Integer myTime;

    public DinerThread(Integer id, Order order, Integer timeArrived) {
        this.myId = id;
        this.myOrder = order;
        this.timeArrived = timeArrived;
    }

    private void waitForYourArrival() {
        Manager.getCurrentTime(this);
        while (myTime < timeArrived) {Manager.getCurrentTime(this);}
        Event.logEvent(myTime, String.format("Diner %d arrives", myId));
    }

    private void seat() {

    }

    private void waitForYourOrder() {

    }

    private void eat() {

    }

    private void getOut() {

    }

    @Override
    public void run() {
        waitForYourArrival();
        seat();
        waitForYourOrder();
        eat();
        getOut();
    }

    public void setTime(Integer globalTime) {
        this.myTime = globalTime;
    }
}
