package car;

public class Engine {

    private static Integer count = 0;

    private int ID;

    public Engine() {
        synchronized (count) {
            ID = count++;
        }
    }

    public String getID() {
        return String.valueOf(this.ID);
    }
}
