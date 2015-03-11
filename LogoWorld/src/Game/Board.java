package Game;

import org.apache.log4j.*;

/**
 * This class to store the state board.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Board {

    private static final Logger LOGGER = Logger.getLogger(Board.class.getName());

    /** Array for save board marks. */
    private int board[][];

    /** Coord AI. */
    private Point coord = new Point();

    /** Board size. */
    private Point size = new Point();

    /** Initialize flag. */
    private boolean flagInit = false;

    /** Draw mode flag. */
    private boolean flagDraw = false;

    /** View mode flag. */
    private boolean flagView = true;

    /**
     * Set board with size and AI coordinate.
     * Initialize board.
     *
     * @param size Size of board.
     * @param point Coordinate AI
     */
    public void setBoard(Point size, Point point) {
        this.size = size;
        board = new int[size.getXCoord()][size.getYCoord()];
        this.setCoord(point);
        setDraw(false);
        flagInit = true;;
    }

    public Board() {
        board = null;
        flagInit = false;
    }

    public void setDraw(boolean flag) {
        this.flagDraw = flag;
    }

    public boolean getDraw() {
        return this.flagDraw;
    }

    public Point getCoord() {
        return this.coord;
    }

    public void setCoord(Point point) {
        this.coord.setCoord(point);
    }

    public Point getSize() {
        return this.size;
    }

    /**
     * Mark point on board.
     *
     * @param point Point for mark as drawn
     */
    public void markPoint(Point point) {
        if (this.flagInit == false) {
            LOGGER.warn("Board didn't initialize!");
            System.out.println("Board didn't initialize!");
            return;
        }

        this.board[point.getXCoord()][point.getYCoord()] = 1;
    }

    /**
     * Unmark point on board.
     *
     * @param point Point for mark as not painted.
     */
    public void unmarkPoint(Point point) {
        if (this.flagInit == false) {
            LOGGER.warn("Board didn't initialize!");
            System.out.println("Board didn't initialize!");
            return;
        }

        this.board[point.getXCoord()][point.getYCoord()] = 0;
    }

    public int isMark(int x, int y) {
        return board[x][y];
    }

    public boolean isInit() {
        return flagInit;
    }

    public void setInit() {
        flagInit = true;
    }

    public void setViewMode(boolean flag) {
        flagView = flag;
    }

    public boolean isView() {
        return flagView;
    }
}
