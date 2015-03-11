package view.window;

import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * This class is window "Help".
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Help extends JFrame {

    private JTextArea info;

    public Help(String help) {
        super("Help");

        info = new JTextArea(help);
        info.setEditable(false);
        this.add(info);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

}
