/**
 * Contain all types of action, that may occur.
 */
public enum ActionType {

    /** User want login to server */
    LOGIN,

    /** Some error */
    ERROR,

    /** Success action */
    SUCCESS,

    /** Get list of all users */
    LIST,

    /** Logout from server */
    LOGOUT,

    /** Some user login */
    USER_LOGIN,

    /** Some user logout */
    USER_LOGOUT,

    /** Create new user on server */
    NEW_USER,

    /** Create new session */
    NEW_SESSION,

    /** User leave conference */
    USER_LEAVE_CONFERENCE,

    /** User join to conference */
    USER_JOIN_TO_CONFERENCE,

    /** Join to session */
    JOIN_TO_SESSION

}
