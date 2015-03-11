package model;

import java.util.HashMap;

/**
 * This class store all scores in game.
 *
 * @see Score
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class Scores{

    HashMap<String, Score> scores = new HashMap<String, Score>();

    String scoresFile;

    public Scores(String path) {
        scoresFile = path;
    }

    public HashMap<String, Score> getAllScores() {
        return this.scores;
    }

    public Score getScore(String levelName) {
        if (scores.containsKey(levelName)) {
            return scores.get(levelName);
        }
        return null;
    }

    public void addScore(String levelName, String playerName, int numberOfStep) {
        if (scores.containsKey(levelName)) {
            int currentStep = scores.get(levelName).getNumberOfStep();

            if (numberOfStep < currentStep) {
                scores.put(levelName, new Score(playerName, numberOfStep));
            }
        } else {
            scores.put(levelName, new Score(playerName, numberOfStep));
        }

        ScoresTool.writeScoresToFile(scoresFile, this);
    }


}
