import dialog.User;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

/**
 * This class store information about users.
 *
 * @author Dozmorov Nikolay
 * @version 0.2
 * @see User
 */
public class UserList extends Observable {

    /** Hashmap with all users. Key is nickname. Value is User. */
    private final HashMap<String, User> users;

    /**
     * Default constructor.
     */
    public UserList() {
        users = new HashMap<>();
    }

    /**
     * Add user in user list.
     *
     * @param user user for adding.
     */
    public void add(User user) {
        users.put(user.getName(), user);

        //Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<String, String>("ADD", user.getName()));
    }

    /**
     * Remove user from user list by username.
     *
     * @param username user's username.
     */
    public void remove(String username) {
        users.remove(username);

        //Notify observers.
        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<String, String>("REMOVE", username));
    }

    /**
     * Get number of user.
     *
     * @return number of user.
     */
    public int getCount() {
        return users.size();
    }

    /**
     * Get existence user in list.
     *
     * @param name name of user.
     * @return true if user exist.
     */
    public boolean isExist(String name) {
        return users.containsKey(name);
    }

    /**
     * Get user by name.
     *
     * @param name name of user.
     */
    public User get(String name) {
        return users.get(name);
    }

    /**
     * Get all users in userlist.
     *
     * @return all users.
     */
    public Collection<User> getAll() {
        return users.values();
    }
}
