package controller;

import model.Board;
import view.View;

/**
 * This class is controller of view.
 * It is controller in MVC.
 *
 * @see Board
 * @see View
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class ViewController {

    View view;

    Board board;


    public ViewController(Board board) {
        this.board = board;
    }

    public void resetGame() {
        view.resetGame();
        board.reset();
    }

    public void startGame(String levelName) {
        board.startGame(levelName);
        view.startGame();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void restartGame() {
        view.restartGame();
        board.restart();
    }

    public void stopGame() {
        view.resetGame();
        board.endGame();
    }

}
