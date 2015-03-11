package car;

public class Accessory {

    private static Integer count = 0;

    private int ID;

    public Accessory() {
        synchronized (count) {
            ID = count++;
        }
    }

    public String getID() {
        return String.valueOf(this.ID);
    }
}
