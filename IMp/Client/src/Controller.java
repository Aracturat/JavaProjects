import contact.User;
import dialog.Message;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents class for work with server by means of Action.
 *
 * @see Action
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class Controller extends Observable {

    /** User client */
    private final Client client;

    /** Server listener */
    private AbstractServerListener listener;

    /** Name of user */
    private String username;

    private HashMap<String, Conference> conferences;

    private UserList userlist;

    /**
     * Default constructor.
     *
     * @param client client for controlling.
     */
    public Controller(Client client) {
        this.client = client;
        this.conferences = new HashMap<>();
        this.userlist = new UserList();
    }

    /**
     * Set server listener.
     *
     * @param listener server listener.
     */
    public void setListener(AbstractServerListener listener) {
        this.listener = listener;
    }

    /**
     * Process some action.
     *
     * @param action action for processing.
     */
    public void process(Action action) {
        switch (action.getType()) {
            case USER_LOGIN:
                userLogin(action);
                break;

            case USER_LOGOUT:
                userLogout(action);
                break;

            case SUCCESS:
                success(action);
                break;

            case ERROR:
                error(action);
                break;

            case USER_JOIN_TO_CONFERENCE:
                userJoin(action);
                break;

            case USER_LEAVE_CONFERENCE:
                userLeave(action);

            default:
                unknown(action);
                break;
        }
    }

    /**
     * Process SUCCESS action.
     *
     * @param action processed action.
     */
    private void success(Action action) {
        switch (ActionType.valueOf(action.get("ANSWER"))) {
            case LOGIN:
                System.out.println("Successfully login.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("LOGIN", action.get("NAME")));
                username = action.get("NAME");
                break;

            case NEW_USER:
                System.out.println("Successfully create new user.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("NEW_USER", username));
                this.disconnect();
                break;

            case LIST:
                System.out.println("Successfully get list of user.");
                for (int i = 0; i < Integer.parseInt(action.get("COUNT")); i++) {
                    String username = action.get(String.valueOf(i));
                    userlist.add(new User(username, null));
                    this.setChanged();
                    this.notifyObservers(new AbstractMap.SimpleEntry<>("ADD", username));
                }
                break;

            case LOGOUT:
                System.out.println("Successfully logout.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("LOGOUT", ""));
                this.username = null;
                this.disconnect();
                break;

            case NEW_SESSION:
                System.out.println("New session created");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("NEW_SESSION", action.get("SESSION_NAME")));
                break;

            case JOIN_TO_SESSION:
                System.out.println("Join to session");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("JOIN_TO_SESSION", action.get("SESSION_NAME")));
                break;

            default:

                 break;
        }
    }

    /**
     * Process ERROR action.
     *
     * @param action processed action.
     */
    private void error(Action action) {
        switch (ActionType.valueOf(action.get("ANSWER"))) {
            case LOGIN:
                System.out.println("Login error.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("ERROR LOGIN", action.get("REASON")));
                break;

            case NEW_USER:
                System.out.println("Error in creating new user.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("ERROR NEW_USER", action.get("REASON")));
                this.disconnect();
                break;

            case NEW_SESSION:
                System.out.println("Error in creation new session.");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("ERROR NEW_SESSION", action.get("REASON")));
                break;

            case JOIN_TO_SESSION:
                System.out.println("Error in joining to session");
                this.setChanged();
                this.notifyObservers(new AbstractMap.SimpleEntry<>("ERROR JOIN_TO_SESSION", action.get("REASON")));
                break;

            default:

                break;
        }
    }

    /**
     * Process LOGOUT action.
     *
     * @param action processed action.
     */
    private void userLogout(Action action) {
        System.out.println("User logout to server.");
        String name = action.get("NAME");
        userlist.remove(name);

        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<>("REMOVE", name));
    }

    /**
     * Process USER_LOGIN action.
     *
     * @param action processed action.
     */
    private void userLogin(Action action) {
        System.out.println("New user login to server.");
        String name = action.get("NAME");
        userlist.add(new User(name, null));

        this.setChanged();
        this.notifyObservers(new AbstractMap.SimpleEntry<>("ADD", name));
    }

    /**
     * Process USER_JOIN_TO_CONFERENCE action.
     *
     * @param action processed action.
     */
    private void userJoin(Action action) {
        String sessionName = action.get("SESSION_NAME");
        String name = action.get("NAME");

        // Get conference and user.
        Conference conference = conferences.get(sessionName);
        User user = userlist.get(name);

        // Add user in conference.
        conference.addUser(user);
    }

    /**
     * Process USER_LEAVE_CONFERENCE action.
     *
     * @param action processed action.
     */
    private void userLeave(Action action) {
        String sessionName = action.get("SESSION_NAME");
        String name = action.get("NAME");

        // Get conference.
        Conference conference = conferences.get(sessionName);

        // Add user in conference.
        conference.removeUser(name);
    }

    /**
     * Process unknown action.
     *
     * @param action processed action.
     */
    private void unknown(Action action) {
        //Create answer action ERROR.
        Action answer = new Action(ActionType.ERROR);
        answer.add("REASON", "UNKNOWN COMMAND");
        answer.add("ANSWER", action.getType().toString());

        //Send answer to server.
        listener.send(answer);
    }

    /**
     * Create login request to server.
     *
     * @param username name of user.
     */
    public void requestLogin(String username) {
        this.connect();

        if (null != listener) {
            //Create new LOGIN action.
            Action action = new Action(ActionType.LOGIN);
            action.add("NAME", username);

            //Send action to server.
            listener.send(action);
        }
    }

    /**
     * Create logout request to server.
     */
    public void requestLogout() {
        //Create new LOGOUT action.
        Action action = new Action(ActionType.LOGOUT);
        action.add("NAME", username);

        //Send action to server.
        listener.send(action);
    }

    /**
     * Create new user request to server.
     *
     * @param username name of user for creation.
     */
    public void requestNewUser(String username) {
        this.connect();

        if (null != listener) {
            //Create new NEW_USER action.
            Action action = new Action(ActionType.NEW_USER);
            action.add("NAME", username);

            //Send action to server.
            listener.send(action);
        }
    }

    /**
     * Create list request to server.
     */
    public void requestList() {
        //Create new LIST action.
        Action action = new Action(ActionType.LIST);
        action.add("NAME", username);

        //Send action to server.
        listener.send(action);
    }

    /**
     * Create new session request to server.
     *
     * @param username name of user.
     */
    public void requestNewSession(String username) {
        //Create new NEW_SESSION action.
        Action action = new Action(ActionType.NEW_SESSION);
        action.add("NAME", this.username);
        action.add("AIM", username);

        //Send action to server.
        listener.send(action);
    }

    /**
     * Create join session request to server.
     *
     * @param sessionName name of session.
     */
    public void requestJoinToSession(String sessionName) {
        //Create new NEW_SESSION action.
        Action action = new Action(ActionType.JOIN_TO_SESSION);
        action.add("NAME", this.username);
        action.add("SESSION_NAME", sessionName);

        //Send action to server.
        listener.send(action);
    }

    /**
     * Connect to server.
     *
     * @return success of connection.
     */
    public boolean connect() {
        // Start connection.
        try {
            client.start(InetAddress.getLocalHost(), 5000);
            return true;
        } catch (UnknownHostException e) {
            processError(new AbstractMap.SimpleEntry<>("UNKNOWN HOST", "Unknown server host."));
            return false;
        }
    }

    /**
     * Disconnect from server.
     */
    public void disconnect() {
        client.stop();
    }

    /**
     * Redirect message to chat.
     *
     * @param message message for redirection.
     */
    public void processMessage(Message message) {
        Conference conference = conferences.get(message.getSessionName());
        conference.addMessage(message.getText(), message.getUsername());
    }

    /**
     * Create new message with this text, from session.
     *
     * @param text text of message.
     * @param sessionName name of session.
     */
    public void createMessage(String text, String sessionName) {
        Message message = new Message(text, username, sessionName);
        listener.send(message);
    }

    public void processError(AbstractMap.SimpleEntry<String, String> entry) {
        String key = entry.getKey();
        String value = entry.getValue();

        switch (key) {
            case "SOCKET ERROR":
                setChanged();
                notifyObservers(entry);
                break;

            case "UNKNOWN HOST":
                setChanged();
                notifyObservers(entry);
                break;
        }
    }

    public void addConference(Observer observer, String sessionName) {
        Conference conference = new Conference(sessionName);
        this.conferences.put(sessionName, conference);

        conference.addObserver(observer);
    }

    public void closeAllConference() {
        this.conferences.clear();
    }

    public void addUserListObserver(Observer observer) {
        this.addObserver(observer);
    }
}
