package Com.advancedOS.RestaurantManger.Resources;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    public static ArrayList<Table> availableTables = new ArrayList<Table>();
    public static Lock tableConditionLock = new ReentrantLock();
    public static Condition tableCondition = tableConditionLock.newCondition();
    private Integer id;

    public Table(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Table acquireTable() {
        tableConditionLock.lock();
        try{
            if (availableTables.size() == 0)
                tableCondition.await();
            return availableTables.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            tableConditionLock.unlock();
        }
        return null;
    }

    public static void releaseTable(Table table) {
        tableConditionLock.lock();
        availableTables.add(table);
        tableCondition.signal();
        tableConditionLock.unlock();
    }

}
