package Game.Command;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for switch on function "Switch off view".
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class ViewOff extends Command {

    private static final Logger LOGGER = Logger.getLogger(ViewOff.class.getName());

    public ViewOff() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\tSwitch on mode without view.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Set Without View MODE.
        board.setViewMode(false);

        return false;
    }

}