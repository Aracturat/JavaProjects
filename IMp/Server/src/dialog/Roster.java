package dialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents list of user's contacts.
 *
 * @see Contact
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class Roster implements Serializable {

    /** HashMap with all Contact, key is name of Contact */
    private HashMap<String, Contact> contactList;

    /**
     * Default constructor.
     * Create new empty roster.
     */
    public Roster() {
        contactList = new HashMap<>();
    }

    /**
     * Add new contact to roster.
     *
     * @param contact contact for adding.
     */
    public void addContact(Contact contact) {
        contactList.put(contact.getName(), contact);
    }

    /**
     * Remove contact by name.
     *
     * @param name name of contact for remove.
     */
    public void removeContact(String name) {
        contactList.remove(name);
    }

    /**
     * Get contact by name.
     *
     * @param name name of contact.
     * @return contact
     */
    public Contact getContact(String name) {
        return contactList.get(name);
    }

    /**
     * Get count of contacts in roster.
     *
     * @return number of contacts.
     */
    public int getSize() {
        return contactList.size();
    }

    /**
     * Get all names of all contacts.
     *
     * @return set with contact's names.
     */
    public Set<String> getAllNames() {
        return contactList.keySet();
    }

    /**
     * Remove all contacts.
     */
    public void clear() {
        contactList.clear();
    }
}
