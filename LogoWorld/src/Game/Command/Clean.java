package Game.Command;

import Game.Point;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for cleaning board.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Clean extends Command {

    private static final Logger LOGGER = Logger.getLogger(Clean.class.getName());

    public Clean() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tClean a board.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Clean board.
        for (int i = 0; i < board.getSize().getXCoord(); i++)
        {
            for (int j = 0; j < board.getSize().getYCoord(); j++)
            {
                board.unmarkPoint(new Point(i, j));
            }
        }

        return true;
    }

}
