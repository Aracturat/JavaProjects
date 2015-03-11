import controller.ViewController;
import model.Board;
import view.View;

public class Main {


    public static void main(String[] args) {
        Board board = new Board();
        ViewController controller = new ViewController(board);

        View view = new View(board, controller);
        board.addObserver(view);
        controller.setView(view);

        view.setVisible(true);
    }
}