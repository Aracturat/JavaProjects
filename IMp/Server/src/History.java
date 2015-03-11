import dialog.Message;

import java.util.LinkedList;

/**
 * Represents storage of dialog's history
 *
 * @see dialog.Message
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class History {

    /** LinkedList with all Message */
    private final LinkedList<Message> history;

    /**
     * Default constructor.
     */
    public History() {
        history = new LinkedList<Message>();
    }

    /**
     * Add new message in history.
     *
     * @param message new message for history.
     */
    public void addMessage(Message message) {
        history.add(message);
    }

    /**
     * Clear history.
     */
    public void clear() {
        history.clear();
    }
}
