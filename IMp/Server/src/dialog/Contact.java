package dialog;

import java.awt.Image;
import java.io.Serializable;

/**
 * This class represents information about contact.
 *
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class Contact implements Serializable {

    /** Nickname of contact */
    private final String nickname;

    /** Avatar of contact */
    private final Image icon;

    /**
     * Create new contact.
     *
     * @param name name of contact.
     * @param icon image of avatar.
     */
    public Contact(String name, Image icon) {
        this.nickname = name;
        this.icon = icon;
    }

    /**
     * Get contact's name.
     *
     * @return name
     */
    public String getName() {
        return this.nickname;
    }

    /**
     * Get avatar of contact.
     *
     * @return image of avatar.
     */
    public Image getIcon() {
        return this.icon;
    }
}
