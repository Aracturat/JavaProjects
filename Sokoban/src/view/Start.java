package view;

import controller.ViewController;
import model.Board;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is start panel of view.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Start extends JPanel implements Observer {

    Board board;

    ViewController controller;

    JButton start;

    public Start(Board board, ViewController controller) {
        this.board = board;
        this.controller = controller;

        for (String levelName : board.getAllLevel()) {
            this.add(new JLabel(levelName));
            this.add(new JLabel(board.getScore(levelName)));
            this.add(getStart(levelName));
        }

        this.setSize(100, 100);
        this.setLayout(new GridLayout(board.getAllLevel().size(), 3));
        this.setVisible(true);


    }

    private JButton getStart(final String levelName) {
        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.startGame(levelName);
            }
        });

        return start;
    }



    public void update(Observable obj, Object arg) {
        this.removeAll();
        for (String levelName : board.getAllLevel()) {
            this.add(new JLabel(levelName));
            this.add(new JLabel(board.getScore(levelName)));
            this.add(getStart(levelName));
        }

        this.setSize(100, 100);
        this.setLayout(new GridLayout(board.getAllLevel().size(),3));
        this.setVisible(true);

    }

}
