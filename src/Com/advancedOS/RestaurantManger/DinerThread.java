package Com.advancedOS.RestaurantManger;

import Com.advancedOS.RestaurantManger.Resources.Table;

public class DinerThread extends Thread {
    private final Integer myId;
    private final Order myOrder;
    private Table myTable;
    private final Integer timeArrived;
    private Integer myTime;
    private static final Integer TIME_TO_EAT = 30;

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
        myTable = Table.acquireTable();
        Manager.getCurrentTime(this);
        Event.logEvent(myTime, String.format("Diner %d is seated at table %d", myId, myTable.getId()));
    }

    private void waitForYourOrder() {
        CookThread.produceOrder(myOrder);
        synchronized (myOrder.isReady) {
            try {
                myOrder.isReady.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Manager.getCurrentTime(this);
    }

    private void eat() {
        Event.logEvent(myTime, String.format("Diner %d's food is ready. Diner %d starts to eat.", myId, myId));
        Integer beginningToEatTime = myTime;
        while ((myTime - beginningToEatTime) < TIME_TO_EAT) {Manager.getCurrentTime(this);}
    }

    private void getOut() {
        Table.releaseTable(myTable);
        Event.logEvent(myTime, String.format("Diner %d leaves", myId));
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
