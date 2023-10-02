package Com.advancedOS.RestaurantManger;

import Com.advancedOS.RestaurantManger.Resources.MachineController;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum MachineType {
    BURGER,
    FRY,
    COKE,
    NONE
}

public class CookThread extends Thread {
    private static final Lock capacityLock = new ReentrantLock();
    private static final Condition notEmptyDiners = capacityLock.newCondition();
    private static final Condition notFullCooks = capacityLock.newCondition();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private MachineController controller;
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

    private MachineType checkMachineAvailability() {
        if (orderServing.numberOfBurgersRemaining > 0 && !controller.getBurgerMachineBusy())
            return MachineType.BURGER;
        else if (orderServing.numberOfFriesRemaining > 0 && !controller.getFrierMachineBusy())
            return MachineType.FRY;
        else if (!orderServing.isCokePrepared && !controller.getCokeMachineBusy())
            return MachineType.COKE;
        else
            return MachineType.NONE;
    }

    private void getAvailableMachine(MachineType machineType) {
        if (machineType == MachineType.BURGER)
            controller.acquireBurgerMachine();
        else if (machineType == MachineType.FRY)
            controller.acquireFrierMachine();
        else if (machineType == MachineType.COKE)
            controller.acquireCokeMachine();
    }

    private MachineType acquireMachine(){
        controller.machineLock.lock();
        MachineType machineType = checkMachineAvailability();
        while(machineType == MachineType.NONE) {
            try {
                controller.machineCondition.await();
            } catch (InterruptedException e) {
                controller.machineLock.unlock();
                e.printStackTrace();
                return MachineType.NONE;
            }
            machineType = checkMachineAvailability();
        }
        getAvailableMachine(machineType);
        controller.machineLock.unlock();
        return machineType;
    }

    private void wakeupDiner() {
        synchronized (orderServing.isReady) {
            orderServing.isReady.notifyAll();
        }
    }

    private void notifyOrderProducers() {
        capacityLock.lock();
        CookThread.numberOfAvailableCooks++;
        notFullCooks.signal();
        capacityLock.unlock();
    }

    private void doneServing(){
        wakeupDiner();
        notifyOrderProducers();
    }

    private void serveOrder() {
        controller = MachineController.getInstance();
        while (orderServing.numberOfBurgersRemaining > 0 || orderServing.numberOfFriesRemaining > 0 || !orderServing.isCokePrepared) {
            MachineType machineTypeAcquired = acquireMachine();
            if (machineTypeAcquired == MachineType.BURGER) {
                controller.cookBurger(this);
                orderServing.numberOfBurgersRemaining--;
                controller.releaseBurgerMachine();
            } else if (machineTypeAcquired == MachineType.FRY) {
                controller.fry(this);
                orderServing.numberOfFriesRemaining--;
                controller.releaseFrierMachine();
            } else if (machineTypeAcquired == MachineType.COKE) {
                controller.getCoke(this);
                orderServing.isCokePrepared = true;
                controller.releaseCokeMachine();
            }
        }
        // Done serving, next order to be served
        doneServing();
    }

    public Integer getMyTime() {
        return myTime;
    }

    @Override
    public void run() {
       while (!exit) {
            consumeOrder(this);
           if(exit)
               break;
            Manager.getCurrentTime(this);
            Event.logEvent(myTime, String.format("Diner %d's order will be processed by Cook %d",
                    orderServing.dinerId, myId));
            serveOrder();
       }
    }



}
