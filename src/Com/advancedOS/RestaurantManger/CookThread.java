package Com.advancedOS.RestaurantManger;

public class CookThread extends Thread {
    private Integer id;

    public CookThread(Integer id) {
        this.id = id;
    }
}
