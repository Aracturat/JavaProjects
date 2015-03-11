package suppliers;

import car.Accessory;
import storage.Storage;

public class SupplierAccessory extends Supplier<Accessory> {

    public SupplierAccessory(Storage storage, int delay, ThreadGroup group) {
        super(storage, delay, group);
    }

    public void run() {
        while(true) {
            try {
                sleep(delay);
                Accessory accessory = new Accessory();
                storage.add(accessory);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
