package Com.advancedOS.RestaurantManger;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CookThread extends Thread {
    private static final Lock capacityLock = new ReentrantLock();
    private static final Condition notEmptyDiners = capacityLock.newCondition();
    private static final Condition notFullCooks = capacityLock.newCondition();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private Order orderServing;
    private final Integer myId;
    private Boolean exit;
    private Integer myTime;
    public static Integer numberOfAvailableCooks;

    public CookThread(Integer id) {
        exit = false;
        myId = id;
    }

    public void exit() {
        exit = true;
    }

    public Integer getMyId() {
        return myId;
    }

    public void setOrderServing(Order orderServing) {
        this.orderServing = orderServing;
    }

    public void setTime(Integer myTime) {
        this.myTime = myTime;
    }

    public static void produceOrder(Order order) {
        capacityLock.lock();
        if (numberOfAvailableCooks == 0) {
            try {
                notFullCooks.await();
            } catch (InterruptedException e) {
                capacityLock.unlock();
                e.printStackTrace();
            }
        }
        orders.add(order);
        notEmptyDiners.signal();
        capacityLock.unlock();
    }

    public static void consumeOrder(CookThread cook) {
        capacityLock.lock();
        if (orders.size() == 0) {
            try {
                notEmptyDiners.await();
            } catch (InterruptedException e) {
                capacityLock.unlock();
                cook.exit();
                return;
            }
        }
        cook.setOrderServing(orders.remove(0));
        CookThread.numberOfAvailableCooks--;
        capacityLock.unlock();
    }

    private void acquireMachine(){

    }

    private void serveOrder() {

        // Done serving, next order to be served
        orderServing.isReady.notify();
        CookThread.numberOfAvailableCooks++;
        notFullCooks.signal();
    }

    public Integer getMyTime() {
        return myTime;
    }

    @Override
    public void run() {
       while (!exit) {
            consumeOrder(this);
            Manager.getCurrentTime(this);
            Event.logEvent(myTime, String.format("Diner %d's order will be processed by Cook %d",
                    orderServing.dinerId, myId));
            if(exit)
                break;
            serveOrder();
       }
    }



}
