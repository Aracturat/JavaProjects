package suppliers;

import car.Body;
import storage.Storage;

public class SupplierBody extends Supplier<Body> {

    public SupplierBody(Storage storage, int delay, ThreadGroup group) {
        super(storage, delay, group);
    }

    public void run() {
        while(true) {
            try {
                sleep(delay);
                Body body = new Body();
                storage.add(body);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
