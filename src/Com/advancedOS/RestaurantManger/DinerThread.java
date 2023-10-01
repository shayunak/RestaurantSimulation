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


}
