package view;

import controller.ViewController;
import model.Board;
import view.window.*;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class for visualisation menu.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Menu extends JMenuBar {

    private Board board;

    private ViewController controller;

    private JFrame windowAbout;

    private JFrame windowHelp;

    private JFrame windowScores;

    private JFrame windowReset;

    private JMenu game;

    private JMenu help;

    public Menu(Board board, ViewController controller) {
        this.board = board;
        this.controller = controller;

        this.add(getMenuMain());
        this.add(getMenuHelp());
    }

    private JMenu getMenuMain() {
        if (null == game) {
            game = new JMenu("Main");

            JMenuItem scores = new JMenuItem("Scores");
            JMenuItem reset  = new JMenuItem("Reset the game");
            JMenuItem exit   = new JMenuItem("Exit");

            scores.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowScores = new Scores(board);
                    windowScores.setVisible(true);
                }
            });

            reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (null == windowReset) {
                        windowReset = new Reset(controller);
                    }
                    windowReset.setVisible(true);
                }
            });

            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            game.add(scores);
            game.add(reset);
            game.add(exit);
        }

        return game;
    }

    private JMenu getMenuHelp() {
        if (null == help) {
            help = new JMenu("Help");

            JMenuItem viewHelp = new JMenuItem("View help");
            JMenuItem about  = new JMenuItem("About...");

            viewHelp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (null == windowHelp) {
                        windowHelp = new Help(board.getHelp());
                    }
                    windowHelp.setVisible(true);
                }
            });

            about.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (null == windowAbout) {
                        windowAbout = new About(board.getAbout());
                    }
                    windowAbout.setVisible(true);
                }
            });

            help.add(viewHelp);
            help.add(about);
        }

        return help;
    }

}
