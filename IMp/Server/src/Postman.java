import dialog.Message;
import utils.Storage;

/**
 * Represents postman. Work with message, and send their to users.
 *
 * @see Message
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class Postman extends Thread {

    /** Storage of all message */
    private final Storage<Message> storage;

    /** Pool of all sessions */
    private final SessionPool sessionPool;

    /** Controller of server */
    private final Controller controller;

    /**
     * Default constructor.
     * @param sessionPool
     */
    public Postman(SessionPool sessionPool, Controller controller) {
        this.sessionPool = sessionPool;
        this.controller = controller;

        //Creation of storage.
        storage = new Storage<>();

        //Creation thread with postman.
        this.start();
    }

    /**
     * Add message.
     *
     * @param message message for adding.
     */
    public void add(Message message) {
        storage.add(message);
    }

    public void run() {
        while(true) {
            Message m = null;

            synchronized (storage) {
                if (0 != storage.size()) {
                    m = storage.get();
                }
            }
            if (null != m) {
                String sessionName = m.getSessionName();
                Session session = sessionPool.get(sessionName);
                if (null != session) {
                    session.send(m, controller);
                } else {
                    System.err.println("Session doesn't exist.");
                }
            }
        }
    }
}
