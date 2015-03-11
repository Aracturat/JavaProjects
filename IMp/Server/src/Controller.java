import dialog.User;

import java.util.HashMap;

/**
 * Represent class for control all actions of users.
 *
 * @see Action
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class Controller {

    /** Pool of all sessions */
    private final SessionPool sessionPool;

    /** List of all registered users on server */
    private final UserList allUsers;

    /** List of all connected user */
    private final UserList connectedUsers;

    /** List of all connections */
    private final HashMap<String, AbstractUserListener> connections;

    /**
     * Default constructor.
     */
    public Controller(SessionPool pool, UserList allUsers, UserList connectedUsers) {
        this.sessionPool = pool;
        this.connectedUsers = connectedUsers;
        this.allUsers = allUsers;
        this.connections = new HashMap<>();
    }

    /**
     * Process some actions.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    public void process(Action action, AbstractUserListener listener) {
        switch (action.getType()) {
            case NEW_USER:
                newUser(action, listener);
                break;

            case LOGIN:
                login(action, listener);
                break;

            case LOGOUT:
                logout(action, listener);
                break;

            case LIST:
                list(action, listener);
                break;

            case JOIN_TO_SESSION:
                joinToSession(action, listener);
                break;

            case NEW_SESSION:
                newSession(action, listener);
                break;

            default:
                unknown(action, listener);
                break;
        }
    }

    /**
     * Process action NEW_USER.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void newUser(Action action, AbstractUserListener listener) {
        Action answer;

        String name = action.get("NAME");

        //Check existence of user.
        if (false == allUsers.isExist(name)) {
            System.out.println("Create new user");
            //Create new user and add him in userlist.
            User newUser = new User(name, null);
            allUsers.add(newUser);

            //Creation new SUCCESS answer action.
            answer = new Action(ActionType.SUCCESS);
            answer.add("NAME", name);
            answer.add("ANSWER", action.getType().toString());
        } else {
            //Create new ERROR answer action.
            answer = new Action(ActionType.ERROR);
            answer.add("REASON", "USER ALREADY EXIST");
            answer.add("ANSWER", action.getType().toString());
        }

        //Send answer to user.
        listener.send(answer);
    }

    /**
     * Process action LOGIN.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void login(Action action, AbstractUserListener listener) {
        String name = action.get("NAME");
        Action answer;

        if (true == allUsers.isExist(name) &&
            (false == connectedUsers.isExist(name))) {
            System.out.println("Try login.");
            answer = new Action(ActionType.SUCCESS);
            answer.add("ANSWER", action.getType().toString());
            answer.add("NAME", name);
            listener.send(answer);

            Action userLogin = new Action(ActionType.USER_LOGIN);
            userLogin.add("NAME", name);
            for (AbstractUserListener u: connections.values()) {
                u.send(userLogin);
            }

            connections.put(name, listener);
            connectedUsers.add(allUsers.get(name));

            listener.setRegistration(true);
            listener.setUsername(name);

        } else if (true == connectedUsers.isExist(name)) {
            answer = new Action(ActionType.ERROR);
            answer.add("REASON", "USER ALREADY CONNECTED");
            answer.add("ANSWER", action.getType().toString());

            //Send answer to user.
            listener.send(answer);
        } else {
            answer = new Action(ActionType.ERROR);
            answer.add("REASON", "USER DOESN'T EXIST");
            answer.add("ANSWER", action.getType().toString());

            //Send answer to user.
            listener.send(answer);
        }
    }

    /**
     * Process action LOGOUT.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void logout(Action action, AbstractUserListener listener) {
        System.out.println("Logout");
        String name = action.get("NAME");

        //Remove connection from list.
        connections.remove(name);
        connectedUsers.remove(name);

        //Remove users from sessions.
        for (String s : sessionPool.getNames()) {
            if (true == sessionPool.get(s).getUserList().isExist(name)) {
                sessionPool.get(s).getUserList().remove(name);

                //Send logout action in conference.
                Action leaveConference = new Action(ActionType.USER_LEAVE_CONFERENCE);
                leaveConference.add("SESSION_NAME", s);
                leaveConference.add("NAME", name);

                sessionPool.get(s).send(leaveConference, this);
            }
            if (0 == sessionPool.get(s).getUserList().getCount()) {
                sessionPool.remove(s);
            }
        }

        //Create answer.
        Action answer = new Action(ActionType.SUCCESS);
        answer.add("ANSWER", action.getType().toString());

        //Send answer to user and then finalize connection.
        listener.send(answer);
        listener.finalize();

        //Send other users this
        Action userLogout = new Action(ActionType.USER_LOGOUT);
        userLogout.add("NAME", name);
        for (AbstractUserListener u: connections.values()) {
            u.send(userLogout);
        }
    }

    /**
     * Process action JOIN_TO_SESSION.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
     private void joinToSession(Action action, AbstractUserListener listener) {
        Action answer;
        String sessionName = action.get("SESSION_NAME");
        String name = action.get("NAME");

        if (true == sessionPool.isExist(sessionName)) {
            //Create answer.
            answer = new Action(ActionType.SUCCESS);
            answer.add("ANSWER", action.getType().toString());
            answer.add("SESSION_NAME", sessionName);
            answer.add("NAME", name);

            listener.send(answer);

            Action joinAction = new Action(ActionType.USER_JOIN_TO_CONFERENCE);
            joinAction.add("SESSION_NAME", sessionName);
            joinAction.add("NAME", name);

            Session session = sessionPool.get(sessionName);
            session.addUser(allUsers.get(name));
            session.send(joinAction, this);
        } else {
            //Create answer.
            answer = new Action(ActionType.ERROR);
            answer.add("ANSWER", action.getType().toString());
            answer.add("REASON", "SESSION DOESN'T EXIST");

            listener.send(answer);
        }
     }

    /**
     * Process action LIST.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void list(Action action, AbstractUserListener listener) {
        Action answer;

        //Create SUCCESS answer action.
        answer = new Action(ActionType.SUCCESS);

        //Get all user on server and add their in answer.
        int i = 0;
        for (User user : connectedUsers.getAll()) {
            answer.add(String.valueOf(i++), user.getName());
        }
        answer.add("COUNT", String.valueOf(i));
        answer.add("ANSWER", action.getType().toString());

        //Send answer to user.
        listener.send(answer);
    }

    /**
     * Process action NEW_SESSION.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void newSession(Action action, AbstractUserListener listener) {
        Action answer;

        //Get session's name.
        String username = action.get("NAME");
        String aimname = action.get("AIM");

        String sessionName =  new String(username + " " + aimname);
        System.out.println(sessionName);

        if (false == sessionPool.isExist(sessionName)) {
            //Create new session and add new user.
            Session session = new Session(sessionName);

            session.addUser(connectedUsers.get(username));
            session.addUser(connectedUsers.get(aimname));

            //Add new session in session pool.
            sessionPool.add(session);

            //Create answer action.
            answer = new Action(ActionType.SUCCESS);
            answer.add("SESSION_NAME", sessionName);
            answer.add("NAME", username);
            answer.add("AIM", aimname);
            answer.add("ANSWER", action.getType().toString());

            //Send answer to user.
            session.send(answer, this);

            Action joinActionUser = new Action(ActionType.USER_JOIN_TO_CONFERENCE);
            joinActionUser.add("SESSION_NAME", sessionName);
            joinActionUser.add("NAME", username);

            Action joinActionAim = new Action(ActionType.USER_JOIN_TO_CONFERENCE);
            joinActionAim.add("SESSION_NAME", sessionName);
            joinActionAim.add("NAME", aimname);

            session.send(joinActionUser, this);
            session.send(joinActionAim, this);
        } else {
            answer = new Action(ActionType.JOIN_TO_SESSION);
            answer.add("SESSION_NAME", sessionName);
            answer.add("NAME", username);
            joinToSession(answer, listener);
        }
    }

    /**
     * Process unknown action.
     *
     * @param action processed action.
     * @param listener listener who received action.
     */
    private void unknown(Action action, AbstractUserListener listener) {
        //Create answer action ERROR.
        Action answer = new Action(ActionType.ERROR);
        answer.add("REASON", "UNKNOWN COMMAND");
        answer.add("ANSWER", action.getType().toString());

        //Send answer to user.
        listener.send(answer);
    }

    /**
     * Get listener by User.
     *
     * @param user user whose listener.
     */
    public AbstractUserListener getListener(User user) {
        return connections.get(user.getName());
    }

    public void disconnect(AbstractUserListener listener) {
        String name = listener.getUsername();
        connectedUsers.remove(name);
        connections.remove(name);

        //Send other users this
        Action userLogout = new Action(ActionType.USER_LOGOUT);
        userLogout.add("NAME", name);
        for (AbstractUserListener u: connections.values()) {
            u.send(userLogout);
        }
    }
}

