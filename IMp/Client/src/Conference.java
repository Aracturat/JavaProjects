
import contact.User;
import dialog.Message;

import java.util.AbstractMap;
import java.util.Observable;

/**
 * Represents conference between users.
 *
 * @author Dozmorov Nikolay.
 * @see UserList, User, History
 * @version 0.1
 */
public class Conference extends Observable {

    /** History of conversation */;
    private final History history;

    /** List of users */
    private final UserList userlist;

    /** Name of session */
    private final String name;

    /**
     * Default constructor.
     *
     * @param name name of session.
     */
    public Conference(String name) {
        this.name = name;

        // Create empty history and userlist.
        history = new History();
        userlist = new UserList();
    }

    /**
     * Add user in conference.
     *
     * @param user user for adding.
     */
    public void addUser(User user) {
        if (false == userlist.isExist(user.getName())) {
            this.userlist.add(user);

            // Notify observers.
            this.setChanged();
            this.notifyObservers(new AbstractMap.SimpleEntry<>(ViewActionType.USER_JOIN, user.getName()));
        }
    }

    /**
     * Remove user from conference.
     *
     * @param name name of user.
     */
    public void removeUser(String name) {
        if (true == userlist.isExist(name)) {
            this.userlist.remove(name);

            // Notify observers.
            this.setChanged();
            this.notifyObservers(new AbstractMap.SimpleEntry<>(ViewActionType.USER_LEAVE, name));
        }
    }

    /**
     * Add new message in conference.
     *
     * @param text text of message.
     * @param username name of user, whom message.
     */
    public void addMessage(String text, String username) {
        // Add message in history,
        history.addMessage(new Message(text, username, this.name));

        // Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<>(ViewActionType.NEW_MESSAGE, username + " : " + text));
   }
}
