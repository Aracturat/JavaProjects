import java.io.*;
import java.net.Socket;

import dialog.Message;

/**
 * This class represents abstract socket listener.
 * Are used for read and write message from server.
 *
 * @see Message
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public abstract class AbstractServerListener extends Thread {

    /** Socket for listening */
    protected final Socket socket;

    /** Controller */
    protected Controller controller;

    /** Status of connection */
    protected boolean connectionStatus;

    /**
     * Default constructor.
     *
     * @param socket socket for listening.
     */
    public AbstractServerListener(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;

        connectionStatus = true;
    }

    abstract public void run();

    /**
     * Send message to server.
     *
     * @param message message for send.
     */
    abstract public void send(Message message);

    /**
     * Send action to server.
     *
     * @param action action for send.
     */
    abstract public void send(Action action);

    /**
     * Handle incoming object.
     *
     * @param object incoming object.
     */
    public void handle(Object object) {
        try {
            if (object instanceof Message) {
                Message message = (Message) object;
                controller.processMessage(message);
            } else if (object instanceof Action) {
                Action action = (Action) object;
                controller.process(action);
            }
        } catch (ClassCastException e) {
            System.err.println("Client handle unknown object");
        }

    }

    /**
     * Finalize. Close all stream, and socket.
     */
    public void finalize() {
        try {
            connectionStatus = false;
            super.finalize();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
