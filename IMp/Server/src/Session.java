import dialog.Message;
import dialog.User;

/**
 * Represents user's work session.
 *
 * @see User
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class Session {

    /** Session's name */
    private final String name;

    /** Users in session */
    private final UserList users;

    /** History of message in session */
    private final History history;

    /**
     * Default constructor.
     * Creation new session without users.
     *
     * @param name session's name.
     */
    public Session(String name) {
        this.name = name;

        users = new UserList();
        history = new History();
    }

    /**
     * Add users in session.
     *
     * @param user user for adding.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Remove user from session.
     *
     * @param name name of user.
     */
    public void remove(String name) {
        users.remove(name);
    }

    /**
     * Send message to all users in session.
     *
     * @param message message for send.
     * @param controller server's controller.
     */
    public void send(Message message, Controller controller) {
        history.addMessage(message);
        for(User user : users.getAll()) {
            controller.getListener(user).send(message);
        }
    }

    /**
     * Send action to all users in session.
     *
     * @param action action for send.
     * @param controller server's controller.
     */
    public void send(Action action, Controller controller) {
        for(User user : users.getAll()) {
            controller.getListener(user).send(action);
        }
    }
    /**
     * Get name of session.
     *
     * @return session's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get userlist of session.
     *
     * @return session's name.
     */
    public UserList getUserList() {
        return users;
    }

    /**
     * Override method toString().
     *
     * @return session's name.
     */
    public String toString() {
        return name;
    }
}
