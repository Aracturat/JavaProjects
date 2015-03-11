package Game;

import Game.Command.Command;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("START");

        // Creation factory, board and view..
        LOGGER.info("Create factory");
        Factory factory = new Factory();

        LOGGER.info("Create Board");
        Board board = new Board();

        LOGGER.info("Create View");
        View view = new View(board);

        //Scaner for console reading.
        Scanner in;

        if (0 == args.length) {
            //Read from console.
            in = new Scanner(System.in);
        }
        else {
            //Read from file.
            try {
                LOGGER.info("Trying read " + args[0]);
                in = new Scanner(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                System.err.println("File not found:" + e.getLocalizedMessage());
                LOGGER.error("File " + args[0] + " not found");
                return;
            }
        }

        //Number of command.
        int countOfCommand = 0;

        //Create command.
        factory.createAllCommand("factory.properties");

        //Set board for controller.
        Command.setBoard(board);

        //Welcome text.
        System.out.println("Welcome to my little programming language LOGO world.");
        System.out.println("If you need help write HELP in command line.");

        while (true)
        {
            //Print " > " with number of this command.
            System.out.print(countOfCommand + " > ");

            //Read next line.
            if (in.hasNextLine()) {
                //index split in line.
                int split = 0;

                //Parameter of command.
                ArrayList<String> param = new ArrayList<String>();

                //Name of command.
                String commandName = "";
                String command = deleteSpaces(in.nextLine()) + ' ';

                LOGGER.info("You enter " + command);

                //Get split in line.
                split = nextSplitWord(command);
                if (split <= command.length())
                {
                    //Get name of command.
                    commandName = command.substring(0, split - 1);

                    //String of parameter
                    command = command.substring(split);

                    //Read parameter from string in param.
                    while (0 != command.length()) {
                        split = nextSplitWord(command);
                        String sub = command.substring(0,split - 1);
                        param.add(sub);
                        command = command.substring(split);
                    }
                }

                //Flag to enable view.
                boolean retFlag = false;
                //Check existing command.
                if (Command.allCommand.containsKey(commandName)) {
                    //Execute command.
                    retFlag = Command.allCommand.get(commandName).exec(param);

                    //Check exit flag.
                    if (true == Command.isExit()) {
                        System.out.println("Good Buy!");
                        in.close();
                        LOGGER.info("END");
                        return;
                    }

                    //Resfresh view.
                    if (true == retFlag) {
                        view.refresh();
                    }

                }
                else {
                    if (commandName.length() != 0) {
                        System.out.println("Command" + commandName + " doesn't exist");
                        LOGGER.warn("Command" + commandName + " doesn't exist");
                    }
                }

                //Increase counter of command.
                countOfCommand++;

            }
            else {
                System.out.println("Good Buy!");
                in.close();
                LOGGER.info("END");
                return;
            }
        }
    }

    /**
     * Function to search end of first word in string.
     *
     * @param command Input string
     * @return Index end of first word in string
     */
    static public int nextSplitWord(String command) {
        //Index last symbol of word.
        int index = 0;

        //Is letter or digit flag.
        boolean flag = true;
        char tempChar = ' ';

        while (flag) {
            if (command.length() > index) {
                tempChar = command.charAt(index);
                //Check char for space.
                if (tempChar == ' ')  {
                    flag = false;
                }
            }
            else {
                flag = false;
            }
            index++;
        }

        return index;
    }

    /**
     * This function delete from input string
     * more then one spaces between words.
     *
     * @param in Input string
     * @return String without more then one spaces between words.
     */
    static public String deleteSpaces(String in) {
        //String builder for return value.
        StringBuilder out = new StringBuilder();

        //Number of space between words.
        int countOfSpace = 0;

        //Loop for all symbol input string.
        for (int i = 0; i < in.length(); i++)
        {
            if (in.charAt(i) == ' ') {
                countOfSpace++;
            }
            else {
                //Add symbols in output string.
                if (countOfSpace > 0) {
                    out.append(' ');
                }

                out.append(in.charAt(i));
                countOfSpace = 0;
            }
        }

        return out.toString();
    }
}
