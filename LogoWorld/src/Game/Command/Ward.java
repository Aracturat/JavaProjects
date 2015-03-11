package Game.Command;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for switch off function "draw on board".
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Ward extends Command {

    private static final Logger LOGGER = Logger.getLogger(Ward.class.getName());

    public Ward() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tSwitch off draw mode.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and exist board.
        if (false == super.check(param.size())) {
            return false;
        }

        //Unset DRAW MODE and unmark AI coordinate.
        board.setDraw(false);
        board.unmarkPoint(board.getCoord());

        return false;
    }

}
