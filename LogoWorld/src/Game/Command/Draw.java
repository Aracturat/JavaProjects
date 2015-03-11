package Game.Command;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for switch on function "draw on board".
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Draw extends Command {

    private static final Logger LOGGER = Logger.getLogger(Draw.class.getName());

    public Draw() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tSwitch on draw mode.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Set DRAW MODE and mark coord of AI.
        board.setDraw(true);
        board.markPoint(board.getCoord());

        return false;
    }

}
