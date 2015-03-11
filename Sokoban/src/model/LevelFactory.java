package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Factory for creation level.
 *
 * @see Level
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
*/
public class LevelFactory {


    public Level createLevel(String path) {
        Scanner input;
        try {
            input = new Scanner(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println("AAAAAAA!!!");
            return null;
        }

        int currentLine = 0;

        int currentColumn = 0;
        int countSpecial = 0;

        int xCoord = 0;
        int yCoord = 0;

        ArrayList<Point> specialPlace = new ArrayList<Point>();
        Vector<Vector<BlockType>> field = new Vector<Vector<BlockType>>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            field.add(new Vector<BlockType>());
            currentColumn = 0;
            for (char c: line.toCharArray()) {

                BlockType type = BlockType.WALL;
                switch (c) {
                    case 'x':
                        type = BlockType.WALL;
                        break;

                    case '.':
                        type = BlockType.SPACE;
                        break;

                    case 't':
                        type = BlockType.PUSHER;
                        xCoord = currentColumn;
                        yCoord = currentLine;
                        break;

                    case '*':
                        type = BlockType.BOX;
                        break;

                    case '&':
                        type = BlockType.SPECIAL;
                        specialPlace.add(new Point(currentColumn, currentLine));
                        countSpecial++;
                        break;

                }
                field.get(currentLine).add(type);
                currentColumn++;
            }
            currentLine++;
        }

        Point special[] = new Point[countSpecial];
        specialPlace.toArray(special);

        return new Level(field, special, new Point(xCoord, yCoord));
    }

    public TreeMap<String, String> createAllLevel(String path) {
        TreeMap<String, String> ret = new TreeMap<String, String>();
        try {
            //Open string as input stream.
            InputStream stream = ClassLoader.getSystemResourceAsStream(path);

            Properties utils = new Properties();
            utils.load(stream);

            for (Map.Entry en: utils.entrySet())
            {
                String key = en.getKey().toString();
                String value = en.getValue().toString();
                Level newLevel = this.createLevel(value);

                if (null != newLevel)
                {
                    newLevel.setName(key);
                    ret.put(key, value);
                }

            }

        }
        catch (IOException e) {
            System.out.println("Cannot load " + path);
        }

        return ret;
    }

}