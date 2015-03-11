package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;


/**
 * This class for write or load from file all scores.
 *
 * @see Score
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class ScoresTool {

    public static Scores getScoresFromFile(String path) {
        Scores scores = new Scores(path);
        try {
            //Open string as input stream.
            FileInputStream stream = new FileInputStream(path);

            Properties utils = new Properties();
            utils.load(stream);


            for (Map.Entry en: utils.entrySet())
            {
                String key = en.getKey().toString();
                String value = en.getValue().toString();
                scores.addScore(key, getPlayerName(value), getScore(value));
            }

        }
        catch (IOException e) {
            System.out.println("Cannot load " + path);
        }

        return scores;
    }

    public static void writeScoresToFile(String path, Scores scores) {
        try {
            //Open string as input stream.
            FileOutputStream stream = new FileOutputStream(path);

            Properties utils = new Properties();

            for (Map.Entry<String, Score> en: scores.getAllScores().entrySet())
            {
                String key = en.getKey();
                Score value = en.getValue();
                utils.setProperty(key, value.getNumberOfStep() + "!" + value.getPlayerName());
            }

            utils.store(stream, "Save results");

        }
        catch (IOException e) {
            System.out.println("Cannot load " + path);
        }
    }

    private static String getPlayerName(String value) {
        int position = 0;
        for(char c: value.toCharArray()) {
            if('!' == c) {
                break;
            }
            position++;
        }
        if (0 != position) {
            return value.substring(position+1);
        }
        return "";
    }

    private static int getScore(String value) {
        int position = 0;
        for(char c: value.toCharArray()) {
            if('!' == c) {
                break;
            }
            position++;
        }
        return Integer.parseInt(value.substring(0,position));
    }
}
