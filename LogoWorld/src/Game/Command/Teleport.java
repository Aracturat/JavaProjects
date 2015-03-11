package Game.Command;

import Game.Point;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for teleport AI in destination point.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Teleport extends Command {

    private static final Logger LOGGER = Logger.getLogger(Teleport.class.getName());

    public Teleport() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 2;
        super.description = "\tTeleport at point (x, y). Use TELEPORT <x> <y>.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Coordinate for teleport.
        int x, y;

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Parse int from parameter list.
        try {
            x = Integer.parseInt(param.get(0));
            y = Integer.parseInt(param.get(1));
        } catch (NumberFormatException e) {
            LOGGER.warn("Parameter " + param + " is missing!");
            System.out.println("Parameter " + param + " is missing!");

            return false;
        }

        //Check if the coordinate is within the boundaries of the field.
        if (((board.getSize().getYCoord() > y) || (board.getSize().getXCoord() > x)) &&
            ((x >= 0) && (y >=0)))
        {
            board.setCoord(new Point(x, y));

            //Mark destination point.
            if (true == board.getDraw())
            {
                board.markPoint(new Point(x, y));
            }

            return true;
        }
        else
        {
            LOGGER.warn("You can't teleport to this point");
            System.out.println("You can't teleport to this point");
            return false;
        }
    }

}
