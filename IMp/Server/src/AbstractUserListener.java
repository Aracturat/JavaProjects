import java.io.*;
import java.net.Socket;

import dialog.Message;

/**
 * This class represents abstract socket listener.
 * Are used for read and write message from users.
 * If get message from user, then redirect message to postman.
 *
 * @see Message, Postman
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public abstract class AbstractUserListener extends Thread {

    /** Socket for listening */
    protected final Socket socket;

    /** Postman, who send message to other users */
    protected final Postman postman;

    /** Controller for processing action */
    protected final Controller controller;

    /** Flag indicate registration on server */
    protected boolean flagOfRegistration;

    /** Username of connected user */
    protected String username;

    /**
     * Default constructor.
     *
     * @param socket socket for listening.
     * @param postman postman for processing message.
     * @param controller controller for processing controller.
     */
    public AbstractUserListener(Socket socket, Postman postman, Controller controller) {
        this.socket = socket;
        this.postman = postman;
        this.controller = controller;

        //Set registration flag in false.
        flagOfRegistration = false;
    }

    public abstract void run();

    /**
     * Send message to user.
     *
     * @param message message for send.
     */
    abstract public void send(Message message);

    /**
     * Send action to user.
     *
     * @param action action for send.
     */
    abstract public void send(Action action);

    /**
     * Handle incoming object.
     *
     * @param object incoming object.
     */
    protected void handle(Object object) {
        try {
            if (object instanceof Message) {
                System.out.println("New message");
                Message message = (Message) object;
                postman.add(message);
            } else if (object instanceof Action) {
                System.out.println("New action");
                Action action = (Action) object;
                controller.process(action, this);
            }
        } catch (ClassCastException e) {
            System.err.println("handle unknown object");
        }
    }

    /**
     * Return status of registration on server.
     *
     * @return registration flag.
     */
    public boolean isRegistered() {
        return flagOfRegistration;
    }

    /**
     * Set status of registration.
     *
     * @param status new registration status.
     */
    public void setRegistration(boolean status) {
        flagOfRegistration = status;
    }

    /**
     * Set username.
     *
     * @param username name of user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get username.
     *
     * @return username name of user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Finalize. Close all stream, and socket.
     */
    public void finalize() {
        try {
            sleep(5000);
            super.finalize();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
