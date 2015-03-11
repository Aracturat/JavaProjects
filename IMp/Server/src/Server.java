import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

public class Server extends Thread {

    private ServerSocket serverSocket;

    private final SessionPool sessionPool;

    private final Postman postman;

    private final Controller controller;

    private final UserList allUsers;

    private final UserList connectedUsers;

    public Server() {
        sessionPool = new SessionPool();
        allUsers = new UserList();
        connectedUsers = new UserList();

        controller = new Controller(sessionPool, allUsers, connectedUsers);

        postman = new Postman(sessionPool, controller);
    }

    public void start(int port) {
        // Try create new ServerSocket.
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server started on " + String.valueOf(port) + " port");
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new UserListener(socket, postman, controller);
                System.out.println("New user connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SessionPool getSessionPool() {
        return this.sessionPool;
    }

    public UserList getAllUsers() {
        return this.allUsers;
    }

    public UserList getConnectedUsers() {
        return this.connectedUsers;
    }

    public Controller getController() {
        return this.controller;
    }
}

