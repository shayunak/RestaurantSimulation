package Com.advancedOS.RestaurantManger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Event {
    private static final Lock lock = new ReentrantLock();
    private static final ArrayList<Event> eventList = new ArrayList<>();
    private Integer time;
    private String message;

    public Event(Integer time, String message) {
        this.time = time;
        this.message = message;
    }

    public Integer getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public static void logEvent(Integer time, String message) {
        eventList.add(new Event(time, message));
    }

    public static void logToConsole() {
        eventList.sort(Comparator.comparing(Event::getTime));
        eventList.forEach(event -> System.out.format("[%d] %s.\n", event.getTime(), event.getMessage()));
    }
}
