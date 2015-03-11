package Game.Command;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for exit from game.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Exit extends Command {

    private static final Logger LOGGER = Logger.getLogger(Exit.class.getName());

    public Exit() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tExit from game.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter.
        if (numOfParams != param.size()) {
            LOGGER.warn("Wrong number of parameters");
            System.out.println("Wrong number of parameters");

            return false;
        }

        //Set EXIT FLAG for exit in future.
        super.setExitFlag();

        return false;
    }

}
