package Game.Command;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.*;

/**
 * This class use for view help.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Help extends Command {

    private static final Logger LOGGER = Logger.getLogger(Help.class.getName());

    public Help() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 0;
        super.description = "\t\tView help.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter.
        if (numOfParams != param.size()) {
            LOGGER.warn("Wrong number of parameters");
            System.out.println("Wrong number of parameters");

            return false;
        }

        //Print information about command.
        for (Map.Entry<String, Command> entry : Command.allCommand.entrySet() )
        {
            System.out.println(entry.getKey() + entry.getValue().description);
        }

        return false;
    }
}
