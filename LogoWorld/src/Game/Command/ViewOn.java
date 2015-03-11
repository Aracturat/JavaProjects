package Game.Command;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for switch off function "Switch off view".
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class ViewOn extends Command {

    private static final Logger LOGGER = Logger.getLogger(ViewOn.class.getName());

    public ViewOn() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tSwitch off mode without view.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Set View MODE.
        board.setViewMode(true);

        return true;
    }

}