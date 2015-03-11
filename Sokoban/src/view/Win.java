package view;

import controller.ViewController;
import model.Board;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class is win panel.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Win extends JPanel {

    ViewController controller;

    Board board;

    String levelName;

    public Win(Board b, ViewController c){
        this.controller = c;
        this.board = b;

        levelName = board.getNameOfCurrentLevel();
        final int steps = board.getSteps();

        this.add(new JLabel("You win. You score is " + String.valueOf(steps) + ". "));
        this.add(new JLabel("Enter your name: "));

        final JTextField name = new JTextField("");
        name.setSize(80,20);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = name.getText();
                board.getScores().addScore(levelName, playerName, steps);
                controller.stopGame();
            }
        });
        this.add(name);

        this.add(ok);
        this.setLayout(new GridLayout(2,2));

    }

}
