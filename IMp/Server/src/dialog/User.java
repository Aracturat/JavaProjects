package dialog;

import java.awt.Image;

/**
 * This class represents information about user. Extends Contact.
 *
 * @see Contact
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class User extends Contact {

    /** Roster of user */
    private final Roster roster;

    /**
     * Create new user.
     *
     * @param name name of user.
     * @param icon image of avatar.
     */
    public User(String name, Image icon) {
        super(name, icon);
        this.roster = new Roster();
    }

    /**
     * Get roster of user.
     *
     * @return roster.
     */
    public Roster getRoster() {
        return roster;
    }
}
