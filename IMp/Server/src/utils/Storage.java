package utils;

import java.util.LinkedList;

/**
 * Represents storage of anything.
 * @param <T> type of anything.
 *
 * @author Dozmorov Nikolay.
 * @version 0.1
 */
public class Storage<T> {

    /** Storage of anything */
    private final LinkedList<T> storage;

    /**
     * Default constructor.
     */
    public Storage() {
        this.storage = new LinkedList<>();
    }

    /**
     * Add object in tail.
     *
     * @param t object for adding.
     */
    public void add(T t) {
        synchronized (storage) {
            storage.addLast(t);
        }
    }

    /**
     * Get first element.
     *
     * @return first element of storage.
     */
    public T get() {
        synchronized (storage) {
            return storage.pollFirst();
        }
    }

    /**
     * Get size of storage.
     *
     * @return size.
     */
    public int size() {
        synchronized (storage) {
            return storage.size();
        }
    }
}
