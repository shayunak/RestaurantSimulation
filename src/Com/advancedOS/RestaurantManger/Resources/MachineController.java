package Com.advancedOS.RestaurantManger.Resources;

import Com.advancedOS.RestaurantManger.CookThread;
import Com.advancedOS.RestaurantManger.Event;
import Com.advancedOS.RestaurantManger.Manager;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Singleton Class
public class MachineController {
    public final Lock machineLock;
    public final Condition machineCondition;
    private final Integer TIME_TO_PREPARE_BURGER = 5;
    private final Integer TIME_TO_PREPARE_FRY = 3;
    private final Integer TIME_TO_PREPARE_COKE = 1;
    private Boolean isBurgerMachineBusy;
    private Boolean isFrierMachineBusy;
    private Boolean isCokeMachineBusy;
    private static MachineController controller;

    public static void initializedMachineController() {
        MachineController.controller = new MachineController();
    }

    public static MachineController getInstance() {
        return MachineController.controller;
    }

    public MachineController() {
        this.isBurgerMachineBusy = false;
        this.isFrierMachineBusy = false;
        this.isCokeMachineBusy = false;
        this.machineLock = new ReentrantLock();
        this.machineCondition = machineLock.newCondition();
    }

    public Boolean getBurgerMachineBusy() {
        return isBurgerMachineBusy;
    }

    public Boolean getFrierMachineBusy() {
        return isFrierMachineBusy;
    }

    public Boolean getCokeMachineBusy() {
        return isCokeMachineBusy;
    }

    public void acquireBurgerMachine(){
        isBurgerMachineBusy = true;
    }

    public void acquireFrierMachine(){
        isFrierMachineBusy = true;
    }

    public void acquireCokeMachine(){
        isCokeMachineBusy = true;
    }

    public void releaseBurgerMachine(){
        isBurgerMachineBusy = false;
    }

    public void releaseFrierMachine(){
        isFrierMachineBusy = false;
    }

    public void releaseCokeMachine(){
        isCokeMachineBusy = false;
    }

    public void cookBurger(CookThread cook){
        Manager.getCurrentTime(cook);
        Integer timeStartedCooking = cook.getMyTime();
        Event.logEvent(timeStartedCooking, String.format("Cook %d takes the machine for Buckeye Burger", cook.getMyId()));
        while (cook.getMyTime() - timeStartedCooking < TIME_TO_PREPARE_BURGER)
            Manager.getCurrentTime(cook);
    }

    public void fry(CookThread cook){
        Manager.getCurrentTime(cook);
        Integer timeStartedCooking = cook.getMyTime();
        Event.logEvent(timeStartedCooking, String.format("Cook %d takes the machine for Brutus Fries", cook.getMyId()));
        while (cook.getMyTime() - timeStartedCooking < TIME_TO_PREPARE_FRY)
            Manager.getCurrentTime(cook);
    }

    public void getCoke(CookThread cook){
        Manager.getCurrentTime(cook);
        Integer timeStartedGetting = cook.getMyTime();
        Event.logEvent(timeStartedGetting, String.format("Cook %d takes the machine for Coke", cook.getMyId()));
        while (cook.getMyTime() - timeStartedGetting < TIME_TO_PREPARE_COKE)
            Manager.getCurrentTime(cook);
    }
}
