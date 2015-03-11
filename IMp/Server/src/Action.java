import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents various action (service message).
 *
 * @see ActionType
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class Action implements Serializable {

    /** HashMap of parameters */
    private final HashMap<String,String> parameters;

    /** Type of action */
    private final ActionType type;

    /**
     * Default constructor.
     *
     * @param type type of action.
     */
    public Action(ActionType type) {
        this.type = type;
        parameters = new HashMap<>();
    }

    /**
     * Add parameter.
     *
     * @param name name of parameter.
     * @param value value of parameter.
     */
    public void add(String name, String value) {
        parameters.put(name,value);
    }

    /**
     * Get parameter by name.
     *
     * @param name name of parameter.
     */
    public String get(String name) {
        return parameters.get(name);
    }

    /**
     * Get all names of parameters.
     *
     * @return set of all names.
     */
    public Set<String> getAll() {
        return parameters.keySet();
    }

    /**
     * Remove all parameters,
     */
    public void clear() {
        parameters.clear();
    }

    /**
     * Get action type.
     *
     * @return action type.
     */
    public ActionType getType() {
        return this.type;
    }
}
