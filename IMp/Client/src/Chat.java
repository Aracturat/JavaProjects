import dialog.Message;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.AbstractMap;
import java.util.Observable;
import java.util.Observer;

public class Chat extends JPanel implements Observer {

    /** Controller */
    private final Controller controller;

    /** Model of text area */
    private DefaultListModel textAreaModel;

    /** List of messages */
    private JList textArea;

    /** Model of userList */
    private DefaultListModel userListModel;

    /** List of users */
    private JList userList;

    /** Session name */
    private final String sessionName;

    /** Enter area */
    private JTextArea enterArea;

    /** Scroll for text area */
    private JScrollPane scroll;

    /** Split between text and enter area */
    private JSplitPane split;

    /**
     * Default constructor.
     *
     * @param controller  controller
     * @param sessionName name of session
     */
    public Chat(Controller controller, String sessionName) {
        //Initialize value.
        this.controller = controller;
        this.sessionName = sessionName;

        //Add split pane between chat area and user list.
        this.add(new JSplitPane(SwingConstants.VERTICAL, getSplit(), getUserList()));

        //Set layout and size.
        this.setLayout(new GridLayout(1,1));
        this.setSize(400, 400);
    }

    public void update(Observable ob, Object o) {
        if (o instanceof AbstractMap.SimpleEntry) {
            AbstractMap.SimpleEntry<ViewActionType, String> entry = (AbstractMap.SimpleEntry<ViewActionType, String>) o;

            ViewActionType key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case USER_JOIN:
                    userListModel.addElement(value);
                    textAreaModel.addElement(value + " has joined to conference.");
                    break;

                case USER_LEAVE:
                    userListModel.removeElement(value);
                    textAreaModel.addElement(value + " leave conference.");
                    break;

                case NEW_MESSAGE:
                    textAreaModel.addElement(value);
                    textArea.ensureIndexIsVisible(textAreaModel.size());
                    break;
            }
        }
    }

    private JList getTextArea() {
        if (null == textArea) {
            textAreaModel = new DefaultListModel();
            textArea = new JList(textAreaModel);
        }
        return textArea;
    }

    private JTextArea getEnterArea() {
        if (null == enterArea) {
            enterArea = new JTextArea();
            enterArea.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER ) {
                        String text = enterArea.getText();
                        controller.createMessage(text, sessionName);
                        enterArea.setText("");
                    }
                }
            });
            enterArea.setSize(100,100);
            enterArea.setMinimumSize(new Dimension(100,100));
        }
        return enterArea;
    }

    private JScrollPane getScroll() {
        if (null == scroll) {
            scroll = new JScrollPane(getTextArea());
            scroll.setPreferredSize(new Dimension(100, 100));
        }
        return scroll;
    }

    private JSplitPane getSplit() {
        if (null == split) {
            split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getScroll(), getEnterArea());
            split.setOneTouchExpandable(true);
            split.setDividerLocation(150);
        }
        return split;
    }

    private JList getUserList() {
        if (null == userList) {
            userListModel = new DefaultListModel();
            userList = new JList(userListModel);
        }
        return userList;
    }
}
