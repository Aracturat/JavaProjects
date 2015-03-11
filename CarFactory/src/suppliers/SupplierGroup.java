package suppliers;

import suppliers.Supplier;

import java.util.LinkedList;

public class SupplierGroup extends ThreadGroup {

    LinkedList<Supplier> threads;

    public SupplierGroup(String name) {
        super(name);

        threads = new LinkedList<Supplier>();
    }

    public void setDelay(int delay) {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).setDelay(delay);
        }
    }

    public void add(Supplier supplier) {
        threads.add(supplier);
    }

    public int size() {
        return threads.size();
    }

    public Supplier at(int i) {
        return threads.get(i);
    }

    public void start() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }
}
