package car;


public class Body {

    private static Integer count = 0;

    private int ID;

    public Body() {
        synchronized (count) {
            ID = count++;
        }
    }

    public String getID() {
        return String.valueOf(this.ID);
    }

}

