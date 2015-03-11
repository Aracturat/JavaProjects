package car;

public class Car {

    private static Integer count = 0;

    private Body body;

    private Engine engine;

    private Accessory accessory;

    private int ID;

    public Car(Body body, Engine engine, Accessory accessory) {
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
        synchronized (count) {
            ID = count++;
        }
    }

    public String getID() {
        return String.valueOf(this.ID);
    }

    public String getAllInfo() {
        return new String("Auto <" + getID()+
                "> (Body: <" + body.getID() +
                ">, Motor: <" + engine.getID() +
                ">, Accessory: <" + accessory.getID()+">)");
    }

}
