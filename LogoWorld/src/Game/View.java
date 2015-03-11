package Game;

import org.apache.log4j.*;

/**
 * Class for display game board.
 *
 * @see Board
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class View {

    private static final Logger LOGGER = Logger.getLogger(View.class.getName());

    /** Board for view. */
    private Board board = null;

    public View(Board b) {
        board = b;
    }

    public void setBoard(Board b) {
        board = b;
    }

    /**
     * Refresh view of board.
     */
    public void refresh() {

        //Check exist board.
        if (false == board.isInit()) {
            LOGGER.error("Board for view doesn't exist!");
            return;
        }

        // Check visibility flag.
        if (board.isView() == false) {
            return;
        }
        //Get board size.
        int x = board.getSize().getXCoord();
        int y = board.getSize().getYCoord();

        //Symbol for visualization board.
        final char unbox            = '\u25A3';
        final char inbox            = '\u2680';
        final char box              = '\u25A2';
        final char cornerLeftUp     = '\u256D';
        final char cornerLeftDown   = '\u2570';
        final char cornerRightUp    = '\u256E';
        final char cornerRightDown  = '\u256F';
        final char vertical         = '\u2577';
        final char horizontal       = '\u2576';

        //Print first line.
        System.out.println();
        System.out.print(cornerLeftUp);
        for (int i = 0; i < x; i++) {
            System.out.print(horizontal);
        }
        System.out.println(cornerRightUp);
        //Print board.
        for (int i = 0; i < y; i++) {
            System.out.print(vertical);
            for (int j = 0; j < x; j++) {
                //Compare print coord with coord of AI.
                if ((board.getCoord().getXCoord() == j) && (board.getCoord().getYCoord() == i)) {
                    System.out.print(inbox);
                } else {
                      if (board.isMark(j, i) == 1) {
                          System.out.print(unbox);
                      } else {
                          System.out.print(box);
                      }
                }
            }
            System.out.println(vertical);
        }

        //Print last line.
        System.out.print(cornerLeftDown);
        for (int i = 0; i < x; i++) {
            System.out.print(horizontal);
        }
        System.out.println(cornerRightDown);
    }
}
