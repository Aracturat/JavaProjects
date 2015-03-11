package suppliers;

import storage.Storage;

public class Supplier<T> extends Thread {

    protected Storage<T> storage;

    protected int delay;

    protected Supplier(Storage<T> storage, int delay, ThreadGroup group) {
        super(group, "Supplier");
        this.storage = storage;
        this.delay = delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return this.delay;
    }
}
