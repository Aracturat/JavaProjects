package Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.*;

import Game.Command.Command;


/**
 * Abstract factory for creation command.
 *
 * @see Command
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Factory {

    private static final Logger LOGGER = Logger.getLogger(Factory.class.getName());

    /**
     * Function for create command by name of class
     *
     * @param str Name of class
     * @return New instance of command
     */
    private Command createCommand(String str)
    {
        //Class for creation.
        Class cl;

        //Return Game.Command.
        Command ret = null;

        try {
            cl = Class.forName(str);
            ret = Command.class.cast(cl.newInstance());

            LOGGER.info("Class " + str + " was created");
        } catch (ClassNotFoundException e1) {
            LOGGER.warn("Class " + str + " was not founded");
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        return ret;
    }

    /**
     * Function for create all command by config file.
     * Line in the configuration file should look like this:
     * NAME=Full.Path.To.Class
     *
     * @param path Path to configuration file
     */
    public void createAllCommand(String path) {
        try {
            //Open string as input stream.
            InputStream stream = ClassLoader.getSystemResourceAsStream(path);

            //Read string.
            Properties utils = new Properties();
            utils.load(stream);

            for (Map.Entry en: utils.entrySet())
            {
                //Name of command.
                String key = en.getKey().toString();

                //Check for exist command.
                if (false == Command.allCommand.containsKey(key))
                {
                    //Create command.
                    Command newCommand = this.createCommand(en.getValue().toString());

                    //Add command in cache.
                    if (null != newCommand)
                    {
                        Command.allCommand.put(key, newCommand);
                    }
                }
            }
        }
        catch (IOException e) {
            LOGGER.warn("Cannot load " + path);
            System.out.println("Cannot load " + path);
        }
    }
}
