package storage;

import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Storage<T> extends Observable {

    private Queue<T> storage;

    private int capacity;

    public Storage(int capacity) {
        storage = new ArrayBlockingQueue<T>(capacity);
        this.capacity = capacity;
    }

    synchronized public boolean isFull() {
        return (storage.size() == capacity);
    }

    public void add(T t) {
        synchronized (storage) {
            while (capacity == storage.size()) {
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storage.add(t);

            setChanged();
            notifyObservers(storage.size());

            storage.notify();
        }
    }

    public T get() {
        synchronized (storage) {
            while (0 == storage.size()) {
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = storage.poll();

            storage.notify();
            return t;
        }
    }

    public void clear() {
        storage.clear();
    }

    synchronized public int getSize() {
        return storage.size();
    }

    public Integer getCapacity() {
        return this.capacity;
    }
}
