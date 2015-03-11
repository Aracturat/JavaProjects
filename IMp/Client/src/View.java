import dialog.*;
import dialog.History;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class View extends JFrame implements Observer {

    private Controller controller;

    private final HashMap<String, Chat> chats;

    private JFrame chatWindow;

    private ViewRoster viewRoster;

    private JTabbedPane chatTab;

    private final FrameStart start;

    private String username;

    private JMenuBar menu;

    public View(Controller controller) {
        super("IMp");

        this.controller = controller;
        controller.addObserver(this);

        chats = new HashMap<>();
        start = new FrameStart(controller);

        this.setJMenuBar(getMenu());

        startChatWindow();

        this.add(start);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void addNewChat(String sessionName) {
        if (false == chats.containsKey(sessionName)) {
            //Creation new chat.
            Chat chat = new Chat(controller, sessionName);

            chats.put(sessionName, chat);

            controller.addConference(chat, sessionName);

            chatTab.add(chat, sessionName);
        }
        chatWindow.setVisible(true);
        chatWindow.setSize(300,300);
    }

    public void update(Observable ob, Object o) {
        if (o instanceof AbstractMap.SimpleEntry) {
            AbstractMap.SimpleEntry<String, String> entry = (AbstractMap.SimpleEntry<String, String>) o;

            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "LOGIN":
                    this.username = value;
                    //Remove start panel, and add roster.
                    this.remove(start);
                    viewRoster = new ViewRoster(controller);
                    this.add(viewRoster);
                    controller.addUserListObserver(viewRoster);

                    //Change title of window.
                    this.setTitle("Imp - " + this.username);
                    //Request list of all users.
                    controller.requestList();
                    break;

                case "LOGOUT":
                    this.username = null;
                    //Remove roster, and add start panel.
                    this.remove(viewRoster);

                    //Close chat window.
                    chatWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    chatWindow.setVisible(false);

                    this.add(start);

                    //Change title of window.
                    this.setTitle("Imp");
                    break;

                case "NEW_SESSION":
                    addNewChat(value);
                    break;

                case "JOIN_TO_SESSION":
                    addNewChat(value);

                    //controller.requestHistory(value);
                    break;

                case "SOCKET ERROR":

                case "UNKNOWN HOST":

                case "ERROR NEW_USER":

                case "ERROR LOGIN":
                    start.process(entry);
                    break;

                case "ERROR NEW_SESSION":

                    break;
            }
        }
        this.repaint();
    }

    private void startChatWindow() {
        chatTab = new JTabbedPane();

        chatWindow = new JFrame("Chats");
        chatWindow.add(chatTab);
        chatWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
        chatWindow.setVisible(false);
        chatWindow.setLocationRelativeTo(null);
    }

    private void stopChatWindow() {
        chatWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        chatWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                controller.closeAllConference();
            }
        });
    }

    private JMenuBar getMenu() {
        if (null == menu) {
            menu = new JMenuBar();

            JMenu main = new JMenu("Main");
            JMenu setting = new JMenu("Setting");
            JMenu about = new JMenu("About");

            JMenuItem exit = new JMenuItem("Exit");
            JMenuItem joinToSession = new JMenuItem("Join to session");
            JMenuItem aboutItem = new JMenuItem("About");
            JMenuItem connections = new JMenuItem("Connections");
            JMenuItem disconnect = new JMenuItem("Disconnect");

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });

            joinToSession.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    final JFrame connectToSession = new JFrame("Connect to session.");
                    connectToSession.add(new JLabel("Enter session's name"));

                    final JTextField text = new JTextField();
                    connectToSession.add(text);

                    JButton connect = new JButton("Connect");
                    connectToSession.add(connect);

                    connect.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            controller.requestJoinToSession(text.getText());
                            connectToSession.setVisible(false);
                        }
                    });

                    connectToSession.add(connect);

                    connectToSession.setLayout(new GridLayout(3, 1));
                    connectToSession.pack();
                    connectToSession.setVisible(true);
                }
            });

            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    controller.requestLogout();
                }
            });
            main.add(joinToSession);
            main.add(exit);
            main.add(disconnect);

            setting.add(connections);

            about.add(aboutItem);

            menu.add(main);
            menu.add(setting);
            menu.add(about);
        }
        return menu;
    }
}
