import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

/**
 * Represents session pool.
 *
 * @see Session
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class SessionPool extends Observable {

    private HashMap<String, Session> sessions;

    /**
     * Default constructor.
     */
    public SessionPool() {
        this.sessions = new HashMap<>();
    }

    /**
     * Create new session.
     *
     * @param name name of new session
     */
    public void create(String name) {
        this.add(new Session(name));
    }

    /**
     * Add session.
     *
     * @param session session for adding.
     */
    public void add(Session session) {
        sessions.put(session.getName(), session);

        //Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<String, String>("ADD", session.getName()));
    }

    /**
     * Remove session by name.
     *
     * @param name session's name.
     */
    public void remove(String name) {
        sessions.remove(name);

        //Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<String, String>("REMOVE", name));
    }

    /**
     * Delete all session.
     */
    public void clear() {
        sessions.clear();

        //Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<String, String>("CLEAR", null));
    }

    /**
     * Get session by name.
     *
     * @param name session's name
     */
    public Session get(String name) {
        return sessions.get(name);
    }

    /**
     * Get existence session in pool.
     *
     * @param name name of session.
     * @return true if session exist.
     */
    public boolean isExist(String name) {
        return sessions.containsKey(name);
    }

    /**
     * Get all list session's names.
     *
     * @return session's names.
     */
    public Set<String> getNames() {
        return sessions.keySet();
    }
}
