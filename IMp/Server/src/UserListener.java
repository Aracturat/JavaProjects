import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dialog.Message;

/**
 * This class represents socket listener and work with objects.
 * Are used for read and write message from users.
 * If get message from user, then redirect message to postman.
 *
 * @see Message, Postman
 * @author Dozmorov Nikolay
 * @version 0.4
 */
public class UserListener extends AbstractUserListener {

    /** Input stream of socket above */
    private ObjectInputStream input;

    /** Output stream of socket above */
    private ObjectOutputStream output;

    /**
     * Default constructor.
     *
     * @param socket socket for listening.
     * @param postman postman for processing message.
     * @param controller controller for processing controller.
     */
    public UserListener(Socket socket, Postman postman, Controller controller) {
        super(socket, postman, controller);

        //Initialize input and output stream.
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Start thread with this listener.
        this.start();
    }

    public void run() {
        try {
            while(true) {
                handle(input.readObject());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I killed myself.");
            controller.disconnect(this);
            this.finalize();
        }
    }

    /**
     * Send message to user.
     *
     * @param message message for send.
     */
    public void send(Message message) {
        try {
            System.out.println("Send message");
            output.writeObject(message);
        } catch (IOException e) {
            System.err.println("Can't send message.");
        }
    }

    /**
     * Send action to user.
     *
     * @param action action for send.
     */
    public void send(Action action) {
        try {
            System.out.println("Send action");
            output.writeObject(action);
        } catch (IOException e) {
            System.err.println("Can't send action.");
        }
    }
}
