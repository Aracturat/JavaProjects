package Game.Command;

import Game.*;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for init board.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Init extends Command{

    private static final Logger LOGGER = Logger.getLogger(Init.class.getName());

    public Init() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 4;
        super.description = "\t\tInit board. Use INIT <width> <height> <x> <y>.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter.
        if (numOfParams != param.size()) {
            LOGGER.warn("Wrong number of parameters.");
            System.out.println("Wrong number of parameters.");

            return false;
        }

        //Size of board, and coord of AI.
        int width;
        int height;
        int x, y;

        //Parse size of board and coord of AI.
        try {
            width = Integer.parseInt(param.get(0));
            height = Integer.parseInt(param.get(1));
            x = Integer.parseInt(param.get(2));
            y = Integer.parseInt(param.get(3));
        } catch (NumberFormatException e) {
            LOGGER.warn("Parameter is missing!");
            System.out.println("Parameter is missing!");

            return false;
        }

        //Set new board and init it.
        super.board.setBoard(new Point(width, height), new Point(x, y));

        return true;
    }

}

