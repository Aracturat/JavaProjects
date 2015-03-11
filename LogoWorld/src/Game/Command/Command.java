package Game.Command;

import Game.Board;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.*;

/**
 * Abstract class command for LOGO world.
 * New instance of this class may be created by abstract factory.
 *
 * @see Game.Factory
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public abstract class Command {

    private static final Logger LOGGER = Logger.getLogger(Command.class.getName());

    /** Board for control, */
    static protected Board board;

    /** Number of parameter this command. */
    protected int numOfParams;

    /** List of all command. */
    static public HashMap<String, Command> allCommand = new HashMap<String, Command>();

    /** Description this command. */
    protected String description;

    /** Flag for exit from game. */
    static private boolean FLAG_EXIT = false;

    /**
     * Abstract function for execute command.
     */
    public abstract boolean exec(ArrayList<String> param);

    static public void setBoard(Board newBoard) {
        board = newBoard;
    }

    /**
     * Check the number of input parameter and initialization board.
     *
     * @param num Number of input parameter
     * @return  The result of pass the check
     */
    protected boolean check(int num) {
        if (numOfParams != num ) {
            LOGGER.warn("Wrong number of parameters");
            System.out.println("Wrong number of parameters");

            return false;
        }
        if (false == board.isInit()) {
            LOGGER.warn("Board didn't initialize!");
            System.out.println("Board didn't initialize!");

            return false;
        }
        return true;
    }

    static public void setExitFlag()
    {
        FLAG_EXIT = true;
    }

    static public boolean isExit() {
        return FLAG_EXIT;
    }

}
