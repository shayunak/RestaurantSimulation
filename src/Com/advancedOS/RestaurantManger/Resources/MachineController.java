package Com.advancedOS.RestaurantManger.Resources;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MachineController {
    private final Lock machineLock;
    private final Condition machineCondition;
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


}
