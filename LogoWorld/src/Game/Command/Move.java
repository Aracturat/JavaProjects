package Game.Command;

import Game.Point;

import java.util.ArrayList;

import org.apache.log4j.*;

/**
 * This class use for move AI on board".
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Move extends Command {

    private static final Logger LOGGER = Logger.getLogger(Move.class.getName());

    public Move() {
        LOGGER.info("Initialize command " + this.getClass().getName());

        super.numOfParams = 2;
        super.description = "\t\tMove AI. Use MOVE [L|R|U|D] <steps>.";
    }

    public boolean exec(ArrayList<String> param) {
        LOGGER.info("Now work " + this.getClass());

        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        //Get coordinate of AI.
        Point coord = this.board.getCoord();
        int x = coord.getXCoord();
        int y = coord.getYCoord();

        //Get size.
        int sizeX = this.board.getSize().getXCoord();
        int sizeY = this.board.getSize().getYCoord();

        //Delta coordinate.
        int delta = 1;

        //Direction for move. H - horizontal, V - vertical.
        char dir = ' ';

        //Length of way.
        int length = 0;

        //Check the parameter of direction.
        if (1 != param.get(0).length())
        {
            LOGGER.warn("Parameter " + param.get(0) + " is missing! Should be [L|R|U|D]");
            System.out.println("Parameter " + param.get(0) + " is missing!");

            return false;
        }

        //Get direction for move AI.
        char direction = param.get(0).charAt(0);

        //Parse int from parameter list.
        try {
            length = Integer.parseInt(param.get(1));
        } catch (NumberFormatException e) {
            LOGGER.warn("Parameter " + param.get(1) + " is missing!");
            System.out.println("Parameter " + param.get(1) + " is missing!");

            return false;
        }

        //Get delta coordinate and direction of move.
        switch (direction) {
            case 'L':
                delta = -1;
                dir = 'H';
                break;
            case 'R':
                delta = 1;
                dir = 'H';
                break;
            case 'U':
                delta = -1;
                dir = 'V';
                break;
            case 'D':
                delta = 1;
                dir = 'V';
                break;
            default:
                System.out.println("This direction doesn't exist");
                LOGGER.warn("This direction doesn't exist");

                return false;
        }

        //Movement.
        for (int i = 0; i < length; i++) {
            //Move AI.
            if ('V' == dir) {
                y = (y + delta) % sizeY;
            } else {
                x = (x + delta) % sizeX;
            }

            //Movement in toroidal world.
            if (x < 0 ) {
                x = sizeX + x;
            }
            if (y < 0) {
                y = sizeY + y;
            }

            //Mark way of movement.
            if (true == board.getDraw())
            {
                board.markPoint(new Point(x, y));
            }
            else {
                board.unmarkPoint(new Point(x, y));
            }

            //Set new coordinate.
            board.setCoord(new Point(x, y));
        }

        return true;
    }

}
