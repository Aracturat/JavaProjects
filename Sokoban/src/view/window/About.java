package view.window;

import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * This class is window "About".
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class About extends JFrame{

    private JTextArea info;

    public About(String about) {
        super("About");

        info = new JTextArea(about);
        info.setEditable(false);
        this.add(info);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

}
