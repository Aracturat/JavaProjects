package view.window;

import controller.ViewController;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is window "Reset".
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Reset extends JFrame {

    JButton ok;

    JButton cancel;

    ViewController controller;

    public Reset(ViewController control) {
        super("Reset the game");

        this.controller = control;

        this.add(new JLabel("Reset?"));
        this.add(getOk());
        this.add(getCancel());

        this.setLayout(new FlowLayout());
        this.setSize(100, 100);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel.getTopLevelAncestor().setVisible(false);
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
                ok.getTopLevelAncestor().setVisible(false);
            }
        });

    }

    private JButton getOk() {
        if (null == ok) {
            ok = new JButton("Ok");
        }

        return ok;
    }

    private JButton getCancel() {
        if (null == cancel) {
            cancel = new JButton("Cancel");
        }

        return cancel;
    }


}
