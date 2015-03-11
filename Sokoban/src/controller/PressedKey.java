package controller;

import model.Board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is simple realisation of KeyListener.
 *
 * @see KeyListener
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class PressedKey implements KeyListener {

    Board board;

    ViewController controller;

    public PressedKey(Board board, ViewController controller) {
        this.board = board;
        this.controller = controller;
    }

    public void keyTyped(KeyEvent keyEvent) {

    }

    public void keyPressed(KeyEvent keyEvent) {
        if (null != board.getCurrentLevel()) {
            int x = board.getCurrentLevel().getCoordAI().getX();
            int y = board.getCurrentLevel().getCoordAI().getY();

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    board.move(x, y, -1, 0);
                    break;

                case KeyEvent.VK_UP:
                    board.move(x, y, 0, 1);
                    break;

                case KeyEvent.VK_RIGHT:
                    board.move(x, y, 1, 0);
                    break;

                case KeyEvent.VK_DOWN:
                    board.move(x, y, 0, -1);
                    break;

                case KeyEvent.VK_F2:
                    controller.restartGame();
                    break;

            }
        }

    }

    public void keyReleased(KeyEvent keyEvent) {

    }

}
