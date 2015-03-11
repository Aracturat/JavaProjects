package dialog;

import java.io.Serializable;

/**
 * Represents message.
 * Store text of message, name of sender, and name of session.
 *
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class Message implements Serializable {

    /** Text of message */
    final private String text;

    /** Name of sender */
    final private String username;

    /** Name of session */
    final private String sessionName;

    /**
     * Default constructor.
     *
     * @param text text of message.
     * @param username name of sender.
     * @param sessionName name of session.
     */
    public Message(String text, String username, String sessionName) {
        this.text = text;
        this.username = username;
        this.sessionName = sessionName;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getSessionName() {
        return sessionName;
    }
}


