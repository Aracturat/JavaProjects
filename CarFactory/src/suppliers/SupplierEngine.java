package suppliers;

import car.Engine;
import storage.Storage;

public class SupplierEngine extends Supplier<Engine> {

    public SupplierEngine(Storage storage, int delay, ThreadGroup group) {
        super(storage, delay, group);
    }

    public void run() {
        while(true) {
            try {
                sleep(delay);
                Engine engine = new Engine();
                storage.add(engine);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
