package view.window;

import model.Board;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.GridLayout;


/**
 * This class is window "Scores".
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Scores extends JFrame {

    public Scores(Board board) {
        super("Scores");

        this.setLayout(new GridLayout(board.getAllLevel().size(),2));

        for (String levelName : board.getAllLevel()) {
            this.add(new JLabel(levelName));
            this.add(new JLabel(board.getScore(levelName)));
        }

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
    }

}
