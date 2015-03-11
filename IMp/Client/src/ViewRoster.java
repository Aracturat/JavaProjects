import contact.Roster;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.AbstractMap;
import java.util.Observable;
import java.util.Observer;

/**
 * This class for view roster.
 *
 * @see Roster
 * @author Dozmorov Nikolay
 * @version 0.1
 */
public class ViewRoster extends JPanel implements Observer {

    Controller controller;

    private DefaultListModel userList;
    private JList list;

    public ViewRoster(Controller controller) {
        this.controller = controller;

        //Getting new JList for display contact.
        this.add(getList());
    }

    private JList getList() {
        if (null == list) {
            userList = new DefaultListModel();
            list = new JList(userList);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    String name = (String) userList.elementAt(listSelectionEvent.getFirstIndex());
                    controller.requestNewSession(name);
                    list.clearSelection();
                }
            });
        }
        return list;
    }

    public void update(Observable ob, Object o) {
        if (o instanceof AbstractMap.SimpleEntry) {
            AbstractMap.SimpleEntry<String, String> entry = (AbstractMap.SimpleEntry<String, String>) o;
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "ADD":
                    userList.addElement(value);
                    break;

                case "REMOVE":
                    userList.removeElement(value);
                    break;
                case "CLEAR":
                    userList.clear();
                    break;
            }

        }
    }

}
