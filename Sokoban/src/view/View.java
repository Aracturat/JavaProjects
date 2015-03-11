package view;

import controller.PressedKey;
import controller.ViewController;
import model.Board;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.Timer;


/**
 * This class for visualisation game.
 * This is view in MVC.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class View extends JFrame implements Observer {

    ViewController controller;

    Board board;

    JPanel levelListPanel;

    JPanel field;

    JLabel score;

    Timer timer;

    JLabel timeLabel;

    JPanel win;

    JPanel scorePanel;

    JMenuBar menu;

    long startTime;

    KeyListener keyListener;

    public void update(Observable obj, Object arg) {
        this.score.setText(String.valueOf(board.getSteps()));
        this.pack();
        this.repaint();

        if (true == board.isEnd()) {
            this.winPanelView();
        }

    }


    public View(Board board, ViewController controller) {
        super("Sokoban");
        this.board = board;
        this.controller = controller;

        this.menu = new Menu(board, controller);
        this.setJMenuBar(menu);
        this.board = board;

        levelListView();

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void winPanelView() {
        if (null != win) {
            this.remove(win);
        }
        this.remove(field);
        this.remove(scorePanel);

        win = new Win(board, controller);
        this.add(win);

        this.setResizable(false);
        this.pack();
        this.repaint();
    }

    public void levelListView() {
        if (null != win) {
            this.remove(win);
        }

        this.levelListPanel = new Start(board, controller);
        this.add(levelListPanel);
        this.repaint();
        this.setResizable(false);
        this.pack();
        this.repaint();
    }


    public void restartGame() {
        if (null != field) {
            this.remove(field);
            this.remove(scorePanel);
        }

        if (null != win) {
            this.remove(win);
        }

        startGame();
    }

    public void resetGame() {
        if (null != win) {
            this.remove(win);
        }

        if (null != field) {
            this.remove(field);
            this.remove(scorePanel);
        } else {
            this.remove(levelListPanel);
        }
        repaint();
        levelListView();
    }

    public void startGame() {
        if (null != field) {
            this.remove(field);
            this.remove(scorePanel);
        }

        if (null != win) {
            this.remove(win);
        }

        if (null != levelListPanel) {
            this.remove(levelListPanel);
        }

        this.getKeyListener();

        this.field = new ViewField(board, this.getWidth(), this.getWidth());
        this.field.repaint();
        this.add(field);
        this.pack();
        this.add(getScorePanel());

        this.setLayout(new FlowLayout());

        this.pack();
        this.repaint();
        this.setVisible(false);
        this.setVisible(true);

        this.setResizable(false);
        this.pack();
    }


    private Timer getTimer() {
        return new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long deltaTime = (System.currentTimeMillis() - startTime) / 1000;

                long hours = deltaTime / 60 / 24 / 60;
                long minutes = (deltaTime - hours * 60 * 24) / 60;
                long seconds = deltaTime - hours * 60 - minutes * 60;
                timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                scorePanel.repaint();
            }
        });
    }

    private JPanel getScorePanel() {
        scorePanel = new JPanel();

        this.startTime = System.currentTimeMillis();
        this.timeLabel = new JLabel("0:0");
        this.timer = getTimer();
        this.timer.start();
        scorePanel.add(new JLabel("Time:"));
        scorePanel.add(timeLabel);

        scorePanel.add(new JLabel(""));
        scorePanel.add(new JLabel("Score:"));
        this.score = new JLabel("0");
        scorePanel.add(score);

        JButton restart = new JButton("Restart");

        restart.setFocusable(false);
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.restartGame();
            }
        });
        scorePanel.add(restart);

        scorePanel.setLayout(new GridLayout(6, 1));
        this.pack();
        scorePanel.repaint();

        return scorePanel;
    }

    public void getKeyListener() {
        if (null == keyListener) {
            keyListener = new PressedKey(board, controller);
            this.addKeyListener(keyListener);
        }
    }
}
