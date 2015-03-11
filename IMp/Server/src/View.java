import dialog.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class View extends JFrame implements Observer {

    private final Server server;

    private final Controller controller;

    private JList sessions;
    private DefaultListModel listOfSession;
    private JScrollPane scrollSession;

    private JList allUsers;
    private DefaultListModel listOfAllUsers;
    private JScrollPane scrollAllUsers;

    private JList connectedUsers;
    private DefaultListModel listOfConnectedUsers;
    private JScrollPane scrollConnectedUsers;

    private JPanel start;

    private JTabbedPane tabbedPane;

    private SystemTray tray = SystemTray.getSystemTray();

    TrayIcon trayIcon;


    public View(Server server) {
        super("Server");

        this.server = server;
        this.controller = server.getController();
        this.add(getTabbedPane());

        this.server.getSessionPool().addObserver(this);
        this.server.getAllUsers().addObserver(this);
        this.server.getConnectedUsers().addObserver(this);

        this.setSize(400,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                toSystemTray();
            }
        });

        //Center window.
        this.setLocationRelativeTo(null);
    }

    private JScrollPane getSessions() {
        if (null == scrollSession) {
            listOfSession = new DefaultListModel();
            sessions = new JList(listOfSession);
            sessions.add(new JScrollBar(SwingConstants.VERTICAL));
            scrollSession = new JScrollPane(sessions);


            sessions.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    final Point point = mouseEvent.getPoint();
                    int index = connectedUsers.locationToIndex(point);
                    if (-1 == index) {
                        return;
                    }
                    final String sessionName = (String) listOfSession.elementAt(index);
                    final JPopupMenu popupMenu = new JPopupMenu("Actions");
                    final Session session = server.getSessionPool().get(sessionName);
                    sessions.clearSelection();

                    JMenuItem list = new JMenuItem("List");
                    list.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            UserList userList = session.getUserList();
                            DefaultListModel model = new DefaultListModel();
                            for (User user : userList.getAll()) {
                                model.addElement(user.getName());
                            }
                            JList viewList = new JList(model);
                            JFrame frame = new JFrame(sessionName);

                            frame.add(viewList);
                            frame.setLayout(new GridLayout(1, 1));
                            frame.pack();
                            frame.setVisible(true);
                        }
                    });
                    popupMenu.add(list);

                    JMenuItem close = new JMenuItem("Close");
                    close.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(close);

                    JMenuItem cancel = new JMenuItem("Cancel");
                    cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(cancel);

                    popupMenu.show(sessions, mouseEvent.getX(), mouseEvent.getY());
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                }
            });
        }
        return scrollSession;
    }

    private JScrollPane getAllUsers() {
        if (null == scrollAllUsers) {
            listOfAllUsers = new DefaultListModel();
            allUsers = new JList(listOfAllUsers);
            allUsers.add(new JScrollBar(SwingConstants.VERTICAL));
            scrollAllUsers = new JScrollPane(allUsers);

            allUsers.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    Point point = mouseEvent.getPoint();
                    int index = connectedUsers.locationToIndex(point);
                    if (-1 == index) {
                        return;
                    }
                    final String username = (String) listOfAllUsers.elementAt(index);
                    final JPopupMenu popupMenu = new JPopupMenu("Actions");
                    allUsers.clearSelection();

                    JMenuItem remove = new JMenuItem("Remove");
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            server.getAllUsers().remove(username);
                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(remove);

                    JMenuItem cancel = new JMenuItem("Cancel");
                    cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(cancel);

                    popupMenu.show(allUsers, mouseEvent.getX(), mouseEvent.getY());
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                }
            });
        }
        return scrollAllUsers;
    }

    private JScrollPane getConnectedUsers() {
        if (null == scrollConnectedUsers) {
            listOfConnectedUsers = new DefaultListModel();
            connectedUsers = new JList(listOfConnectedUsers);
            connectedUsers.add(new JScrollBar(SwingConstants.VERTICAL));
            scrollConnectedUsers = new JScrollPane(connectedUsers);

            connectedUsers.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    Point point = mouseEvent.getPoint();
                    int index = connectedUsers.locationToIndex(point);
                    if (-1 == index) {
                        return;
                    }
                    final String username = (String) listOfConnectedUsers.elementAt(index);
                    final JPopupMenu popupMenu = new JPopupMenu("Actions");
                    connectedUsers.clearSelection();

                    JMenuItem remove = new JMenuItem("Disconnect");
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            Action logout = new Action(ActionType.LOGOUT);
                            logout.add("NAME", username);
                            controller.process(logout, controller.getListener(server.getConnectedUsers().get(username)));

                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(remove);

                    JMenuItem cancel = new JMenuItem("Cancel");
                    cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            popupMenu.setVisible(false);
                        }
                    });
                    popupMenu.add(cancel);

                    popupMenu.show(connectedUsers, mouseEvent.getX(), mouseEvent.getY());
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                }
            });

        }
        return scrollConnectedUsers;
    }

    private JPanel getStart() {
        if (null == start) {
            start = new JPanel();

            final JLabel portLabel = new JLabel("Port: ");
            final JTextField port = new JTextField("5000");
            port.setMinimumSize(new Dimension(120, 20));

            final JButton startButton = new JButton("Start");
            final JButton stopButton = new JButton("Stop");
            stopButton.setEnabled(false);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    server.start(Integer.parseInt(port.getText()));
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                }
            });

            stopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    server.stopSocket();
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
            });

            start.add(portLabel);
            start.add(port);
            start.add(startButton);
            start.add(stopButton);

            start.setLayout(new FlowLayout());
        }
        return start;
    }

    private JTabbedPane getTabbedPane() {
        if (null == tabbedPane) {
            tabbedPane = new JTabbedPane();

            tabbedPane.addTab("Start", getStart());
            tabbedPane.addTab("All users", getAllUsers());
            tabbedPane.addTab("Sessions", getSessions());
            tabbedPane.addTab("Connected users", getConnectedUsers());
        }
        return tabbedPane;
    }

    public void update(Observable ob, Object o) {
        AbstractMap.SimpleEntry<String, String> entry = (AbstractMap.SimpleEntry<String, String>) o;

        String key = entry.getKey();
        String value = entry.getValue();

        if (ob instanceof SessionPool) {
            switch (key) {
                case "ADD":
                    listOfSession.addElement(value);
                    break;

                case "REMOVE":
                    listOfSession.removeElement(value);
                    break;

                case "CLEAR":
                    listOfSession.clear();
                    break;
            }
        } else if (ob instanceof UserList) {
            if (ob.equals(server.getAllUsers())) {
                switch (key) {
                    case "ADD":
                        listOfAllUsers.addElement(value);
                        break;

                    case "REMOVE":
                        listOfAllUsers.removeElement(value);
                        break;
                }
            } else if (ob.equals(server.getConnectedUsers())) {
                switch (key) {
                    case "ADD":
                        listOfConnectedUsers.addElement(value);
                        break;

                    case "REMOVE":
                        listOfConnectedUsers.removeElement(value);
                        break;
                }
            }
        }
    }

    private void toSystemTray() {
        if (true == SystemTray.isSupported()) {
            BufferedImage image = null;

            try {
                image = ImageIO.read(new File("SimpleChat.png"));
            } catch (IOException e) {
                System.out.println("Icon not found");
            }

            trayIcon = new TrayIcon(image, "Server");

            // Set the TrayIcon properties.
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewServer();
                    tray.remove(trayIcon);
                }
            });

            PopupMenu menu = new PopupMenu();
            MenuItem close = new MenuItem("Close");
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    closeApp();
                    tray.remove(trayIcon);
                }
            });
            menu.add(close);

            trayIcon.setPopupMenu(menu);
            // Add the tray image.
            try {
                tray.add(trayIcon);
                this.setVisible(false);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    private void closeApp() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        System.exit(0);
    }

    private void viewServer() {
        this.setVisible(true);
    }
}
