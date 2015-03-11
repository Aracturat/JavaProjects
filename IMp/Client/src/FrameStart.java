import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;

/**
 * Represent start UI.
 */
public class FrameStart extends JPanel {

    /** Controller for request action */
    private final Controller controller;

    /** "Register" button */
    private JButton register;

    /** "Join..." button */
    private JButton join;

    /** Text area for username */
    private JTextArea text;

    /**
     * Default constructor. Create new start field.
     *
     * @param controller controller for action.
     */
    public FrameStart(Controller controller) {
        this.controller = controller;

        text = new JTextArea("USERNAME");
        text.setEditable(true);

        this.add(new JLabel("Enter username:"));
        this.add(text);
        this.add(getRegister());
        this.add(getJoin());

        this.setLayout(new GridLayout(4,1));
    }

    private JButton getRegister() {
        if (null == register) {
            register = new JButton("Register.");
            register.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    controller.requestNewUser(text.getText());
                    text.setText(text.getText());
                }
            });
        }
        return register;
    }

    private JButton getJoin() {
        if (null == join) {
            join = new JButton("Join...");
            join.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    controller.requestLogin(text.getText());
                }
            });
        }
        return join;
    }

    public void process(AbstractMap.SimpleEntry<String, String> entry) {
        String key = entry.getKey();
        String value = entry.getValue();

        switch (key) {
            case "SOCKET ERROR":
                getErrorFrame(value, "SOCKET");
                break;

            case "UNKNOWN HOST":
                getErrorFrame(value, "UNKNOWN HOST");
                break;

            case "ERROR LOGIN":
                getErrorFrame(value, "LOGIN");
                break;

            case "ERROR NEW_USER":
                getErrorFrame(value, "NEW USER");
                break;
        }
    }

    private void getErrorFrame(String cause, String mainCause) {
        JFrame error = new JFrame("ERROR: " + mainCause);
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        error.add(new JLabel(cause));
        error.pack();
        error.setVisible(true);
    }
}
