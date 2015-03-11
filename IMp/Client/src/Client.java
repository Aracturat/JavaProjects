import java.io.IOException;
import java.net.*;
import java.util.AbstractMap;

/**
 * Represent main class for work with any part of client.
 *
 * @author Dozmorov Nokolay.
 * @version 0.2
 */
public class Client {

    /** Socket for work with network */
    private Socket socket;

    /** Listener for work with server */
    private AbstractServerListener listener;

    /** Controller */
    private Controller controller;

    /**
     * Default constructor.
     */
    public Client() {
        controller = new Controller(this);
    }

    /**
     * Start connection.
     *
     * @param host Inet4Address of server
     * @param port port of server
     * @throws IOException
     */
    public void start(InetAddress host, int port) {
        // Try get new Socket.
        try {
            socket = new Socket(host, port);
            listener = new ServerListener(socket, controller);
            controller.setListener(listener);

            System.out.println("Successfully connected");
        } catch (IOException e) {
            controller.processError(new AbstractMap.SimpleEntry<>("SOCKET ERROR","I can't create connection"));
        }
    }

    /**
     * Stop connection.
     */
    public void stop() {
        listener.finalize();
        System.out.println("Successfully disconnected");
    }

    public AbstractServerListener getListener() {
        return this.listener;
    }

    public Controller getController() {
        return this.controller;
    }
}

