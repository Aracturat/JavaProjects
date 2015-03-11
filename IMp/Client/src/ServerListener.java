import java.io.*;
import java.net.Socket;

import dialog.Message;

/**
 * This class represents socket listener.
 * Are used for read and write message from server.
 *
 * @see Message
 * @author Dozmorov Nikolay
 * @version 0.2
 */
public class ServerListener extends AbstractServerListener {

    /** Input stream of socket above */
    private ObjectInputStream input;

    /** Output stream of socket above */
    private ObjectOutputStream output;

    /**
     * Default constructor.
     *
     * @param socket socket for listening.
     */
    public ServerListener(Socket socket, Controller controller) {
        super(socket, controller);

        //Initialize input and output stream.
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.start();
    }

    public void run() {
        try {
            while(connectionStatus) {
                handle(input.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send message to server.
     *
     * @param message message for send.
     */
    public void send(Message message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.err.println("Can't send message.");
        }
    }

    /**
     * Send action to server.
     *
     * @param action action for send.
     */
    public void send(Action action) {
        try {
            output.writeObject(action);
        } catch (IOException e) {
            System.err.println("Can't send action.");
        }
    }
}
